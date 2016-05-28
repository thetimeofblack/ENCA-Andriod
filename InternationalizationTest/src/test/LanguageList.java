package test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class LanguageList {

	private static final Map<Integer, Locale> map = new HashMap<Integer, Locale>() {
		private static final long serialVersionUID = -5151756330293201210L;

		{
			put(0, Locale.ENGLISH);
			put(1, Locale.CHINESE);
		}
	};

	private static final int defaultIndex = 0;
	
	public static int languageIndex=defaultIndex;
	
	public static int getLanguageIndex(){
		return languageIndex;
	}
	
	public static void setLanguageIndex(int index){
		languageIndex=index;
	}

	public static Locale get() {
		return map.get(languageIndex);
	}
}