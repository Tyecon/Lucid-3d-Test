package Data.Assets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class ObjLoader {
	public static class Face {
      public final List<Index> indices;

      public Face() {
         this.indices = new ArrayList();
      }
   }

   public static class Index {
      public final Vector3f v;
      public final Vector2f t;
      public final Vector3f n;

      public Index(Vector3f v, Vector2f t, Vector3f n) {
         this.v = new Vector3f(v);
         this.t = new Vector2f(t);
         this.n = new Vector3f(n);
      }
   }
   
   public static ArrayList<String> loadFile(File file) throws FileNotFoundException {
	   Scanner sc=new Scanner(file);
	   ArrayList<String> obj=new ArrayList();
	   while(sc.hasNext()) {
		   obj.add(sc.nextLine());
	   }
	   return obj;
   }
   
   public static List<Face> load(ArrayList<String> obj) {
      List<Vector3f> vs = new ArrayList();
      List<Vector2f> ts = new ArrayList();
      List<Vector3f> ns = new ArrayList();
      List<Face> fs = new ArrayList();

      for (String line : obj) {
		 if (!(line.startsWith("v")|line.startsWith("vt")|line.startsWith("vn")|line.startsWith("f"))) {continue;}
         String[] pair = line.split(" ", 2);
         if (pair == null) {
            continue;
         }
         String id = pair[0].trim();
         String arg = pair[1].trim();
			switch (id) {
				case "v":
					List<String> xyz = Arrays.asList(arg.split(" "));
					vs.add(new Vector3f(
						Float.parseFloat(xyz.get(0)), 
						Float.parseFloat(xyz.get(1)), 
						Float.parseFloat(xyz.get(2))
					));
					break;
				case "vt":
					List<String> uv = Arrays.asList(arg.split(" "));
					ts.add(new Vector2f(Float.parseFloat(uv.get(0)), Float.parseFloat(uv.get(1))));
					break;
				case "vn":
					List<String> nxyz = Arrays.asList(arg.split(" "));
					ns.add(new Vector3f(Float.parseFloat(nxyz.get(0)),
						Float.parseFloat(nxyz.get(1)),
						Float.parseFloat(nxyz.get(2))
					).normalise(null));
					break;
				case "f":
					Face f = new Face();
					for (String part : Arrays.asList(arg.split(" "))) {
					   List<String> indices = Arrays.asList(part.split("/"));
					   if (indices.size() == 1) {
						  int vIndex = index(Integer.parseInt(indices.get(0)), vs);

						  f.indices.add(new Index(vs.get(vIndex), new Vector2f(0,0), new Vector3f(0,0,0)));
					   } else if (indices.size() == 2) {
						  int vIndex = index(Integer.parseInt(indices.get(0)), vs);
						  int tIndex = index(Integer.parseInt(indices.get(1)), ts);

						  f.indices.add(new Index(vs.get(vIndex), ts.get(tIndex), new Vector3f(0,0,0)));
					   } else if (indices.size() == 3) {
						  int vIndex = index(Integer.parseInt(indices.get(0)), vs);
						  int nIndex = index(Integer.parseInt(indices.get(2)), ns);
						  int tIndex;
						  if (!"".equals(indices.get(1))) {
						  tIndex = index(Integer.parseInt(indices.get(1)), ts);
						  f.indices.add(new Index(vs.get(vIndex), ts.get(tIndex), ns.get(nIndex)));}
						  else {
							  f.indices.add(new Index(vs.get(vIndex), new Vector2f(), ns.get(nIndex)));}
					   } else {
						  System.err.println("face with " + indices.size() + " components");
						  continue;
					   }
					}
					fs.add(f);
					break;
				default:
					break;
			}
      }
      return fs;
   }

   private static int index(int index, List<?> list) {
      if (index > 0) {
         return index - 1;
      }
      return list.size() + index;
   }

   public static void calcNormals(List<Face> faces) {
      Map<Vector3f, List<Face>> coordToNeighbours = new HashMap<>();

      for (Face face : faces) {
         for (Index index : face.indices) {
            Vector3f vec = index.v;
            List<Face> neighbours = coordToNeighbours.get(vec);
            if (neighbours == null) {
               coordToNeighbours.put(vec, neighbours = new ArrayList<>());
            }
            neighbours.add(face);
         }
      }

      for (Face face : faces) {
         for (Index index : face.indices) {
            Vector3f normal = new Vector3f();
            for (Face neighbour : coordToNeighbours.get(index.v)) {
               Vector3f v1 = Vector3f.sub(neighbour.indices.get(0).v, new Vector3f(neighbour.indices.get(1).v), null);
               Vector3f v2 = Vector3f.sub(neighbour.indices.get(0).v, new Vector3f(neighbour.indices.get(2).v), null);
               Vector3f.add(normal, Vector3f.cross(v2, v1, null).normalise(null), null);
            }
            index.n.set(normal.normalise(null));
         }
      }
   }

   public static Vector3f cross(Vector3f a, Vector3f b) {
      return new Vector3f(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
   }

   public static List<Face> asTriangles(List<Face> input) {
      List<Face> output = new ArrayList<>();

      for (Face face : input) {
         if (face.indices.size() == 3) {
            output.add(face);
         } else {
            for (int i = 2; i < face.indices.size(); i++) {
               Face f = new Face();
               f.indices.add(face.indices.get(0));
               f.indices.add(face.indices.get(i - 1));
               f.indices.add(face.indices.get(i - 0));
               output.add(f);
            }
         }
      }

      return output;
   }
}
