package de.fhl.enca.gui.utility;

/**
 * Represent those interfaces can be refreshed after a tag is added.
 * @author Zhaowen.Gong
 * @version 15.07.2016
 * @see de.fhl.enca.gui.view.TagAdderController
 */
public interface Refreshable {

	/**
	 * The action of refreshing the interface
	 */
	public void refresh();
}