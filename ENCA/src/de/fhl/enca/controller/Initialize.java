package de.fhl.enca.controller;

import java.awt.Toolkit;
import java.sql.ResultSet;
import javax.swing.JFrame;
import de.fhl.enca.dao.SQLVisitor;

public final class Initialize {

	private static final Toolkit TOOLKIT = new JFrame().getToolkit();

	public static void initCleaningAgents() {
		ResultSet resultSet = SQLVisitor.visitCleaningAgentsAll();
	}
}