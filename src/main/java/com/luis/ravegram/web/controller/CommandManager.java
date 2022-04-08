 package com.luis.ravegram.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luis.ravegram.web.controller.util.ParameterNames;

public class CommandManager {

	private Map<String, Action> actionsByName = null;

	private List<Action> actions = null;
	
	
	private static CommandManager instance = null;
	
	
	public static CommandManager getInstance() {
		if (instance==null) {
			instance = new CommandManager();
		}
		return instance;
	}
	
	
	private CommandManager() {
		actionsByName = new HashMap<String, Action>();
		
		// En una app web no tiene mucho sentido, ... por tamaño, por undoable ...
		// Las registro para explicar como se haria
		actions = new ArrayList<Action>();
	}
	
	public void doAction(HttpServletRequest request, HttpServletResponse response) {
		
		String actionName = request.getParameter(ParameterNames.ACTION);
		
		Action action = actionsByName.get(actionName);
		
		if (action==null) {
			try {
				String actionClassName = getActionClassName(actionName);
				action = (Action) Class.forName(actionClassName).getConstructor().newInstance();
				actionsByName.put(actionName, action);
			} catch (Exception e) {
				// TODO
				e.printStackTrace();
			}			
		}
		
		// Registro el comando
		actions.add(action);
		
		// Ejecuta el comando
		action.doAction(request, response);
		
					
	}
	
	
	private static final String getActionClassName(String actionName) {
		StringBuilder sb = new StringBuilder(CommandManager.class.getPackageName());
		sb.append(".");
		sb.append(actionName.substring(0,1).toUpperCase());
		sb.append(actionName.substring(1));
		sb.append("Action");
		
		return sb.toString();
		
	}


}
