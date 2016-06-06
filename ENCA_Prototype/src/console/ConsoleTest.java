package console;

import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.Search;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.controller.CleaningAgentFetcher;
import de.fhl.enca.controller.Initialize;

public final class ConsoleTest {

	public static void main(String[] args) {
		Initialize.initTags();
		Initialize.initCleaningAgents();
		Initialize.initRelations();
		
		System.out.println(CleaningAgent.getCleaningAgentAll());
		System.out.println(Tag.getTagsAll());
		System.out.println(Search.search(CleaningAgentFetcher.fetchCleaningAgentsAll(), "a"));
	}
}