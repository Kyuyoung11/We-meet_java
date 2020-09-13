package scheduleAPP;

import java.sql.Date;

public class Schedule {
	private int sche_id;
	private String id;
	private String sche_name;
	private Date date;
	private String location;
	private String participants;

	public Schedule() {

	}

	public Schedule(int sche_id, String id, String sche_name, Date date, String location, String participants) {
		this.sche_id = sche_id;
		this.id = id;
		this.sche_name = sche_name;
		this.date = date;
		this.location = location;
		this.participants = participants;
	}

	public void setSche_id(int sche_id) {
		this.sche_id = sche_id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSche_name(String sche_name) {
		this.sche_name = sche_name;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public int getSche_id() {
		return this.sche_id;
	}

	public String getId() {
		return this.id;
	}

	public String getSche_name() {
		return this.getSche_name();
	}

	public Date getDate() {
		return this.date;
	}

	public String getLocation() {
		return this.location;
	}

	public String getParticipants() {
		return this.participants;
	}

	public String toString() {
		return "Schedule [schedule_id=" + sche_id + ", schedule_name=" + sche_name + ", super_id=" + id + ", date="
				+ date + ", location=" + location + ", participants= " + participants + "]";
	}
}
