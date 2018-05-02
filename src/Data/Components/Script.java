package Data.Components;

import System.Settings;
import com.artemis.Component;
import java.lang.Thread.State;
import java.util.TimerTask;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Script extends Component {
	private final static ScriptEngineManager FACTORY=new ScriptEngineManager();
	private final static ScriptEngine ENGINE=FACTORY.getEngineByName("JavaScript");
	private String name;
	private String content;
	private State status=State.NEW;
	private static String ENVIROMENT="";
	
	public Script() {
		this("Script");
	}
	
	public Script(String name) {
		this(name, "println('Hello World')");
	}
	
	public Script(String name, String content) {
		this.name=name;
		this.content=content;
		Settings.TIMER.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					ENGINE.eval(getContent());
					status=State.RUNNABLE;
				} catch (ScriptException ex) {
					System.out.println("Error in script "+getName());
					ex.printStackTrace(System.err);
					status=State.BLOCKED;
				}
				status=State.TERMINATED;
			}
		}, 0);
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
