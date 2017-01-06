package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jogamp.common.util.Int32ArrayBitfield;


public class Options {
	private int idxOption;
	private int[] list_selected_values;

	private String [][] list_options = {{"time Settings", "Infini", "Set time"}}; // format {title, value1, value2, ..., valueN}
	private int[] timeSelected;
	private int timeIdx;
	
	public Options() {
		idxOption = 0;
		timeIdx = 0;
		timeSelected = new int[]{0,1,0,0};

		list_selected_values = new int[list_options.length];
		for (int cpt=0; cpt<list_options.length; cpt++)
			list_selected_values[cpt]=1;
				
	}

	public int setIdxOption(int idx) {
		if (idx>=0 && idx<list_options.length)
		{
			idxOption = idx;
		}
		return idxOption;
	}

	public int[] setIdxOptionValue(int idx) {
		if (idx> 0 && idx<list_options[idxOption].length)
		{
			list_selected_values[idxOption] = idx;
		}
		return list_selected_values;
	}

	public int getOptionIdx() {
		return idxOption;
	}

	public int[] getOptionValueIdx() {
		return list_selected_values;
	}
	
	public String[][] getOptionsList() {
		return list_options;
	}

	public int setIdxTime(int idx) {
		if (idx>=0 && idx<timeSelected.length)
			timeIdx=idx;
		return timeIdx;
	}

	public int[] setIdxTimeValue(int idx) {
		if (timeSelected[timeIdx]+idx>=0 && timeSelected[timeIdx]+idx<10)
			timeSelected[timeIdx]=timeSelected[timeIdx]+idx;
		return timeSelected;
	}

	public int getTimeIdx() {
		return timeIdx;
	}

	public Integer getTimeSelected() {
		if (list_options[0][list_selected_values[0]]=="Set time") {
			String time = timeSelected[0]+timeSelected[1]+":"+timeSelected[2]+timeSelected[3]; // format 00:00
			return new Integer(Integer.parseInt(time.split(":")[1])+Integer.parseInt(time.split(":")[0])*60);
		}
		return null;
	}
}
