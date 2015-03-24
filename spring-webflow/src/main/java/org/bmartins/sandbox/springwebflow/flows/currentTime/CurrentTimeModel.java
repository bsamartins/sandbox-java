package org.bmartins.sandbox.springwebflow.flows.currentTime;

import java.io.Serializable;
import java.util.Date;

public class CurrentTimeModel implements Serializable {
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
