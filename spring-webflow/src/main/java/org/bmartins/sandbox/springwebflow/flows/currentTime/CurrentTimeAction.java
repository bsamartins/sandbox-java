package org.bmartins.sandbox.springwebflow.flows.currentTime;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class CurrentTimeAction {
	
	public CurrentTimeModel init() {
		CurrentTimeModel model = new CurrentTimeModel();
		model.setDate(new Date());
		return model;
	}
	
	public void onRefresh(CurrentTimeModel model) {
		model.setDate(new Date());
	}
}
