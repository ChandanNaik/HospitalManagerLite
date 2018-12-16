package com.hospitalManager.backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.hospitalManager.fileHandler.ErrorWriter;
import com.hospitalManager.resource.ResourceHandler;

public class QueryHandler {
	Statement stmt = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	ResultSet rs4 = null;
	ResultSet rs5 = null;
	ResultSet rs6 = null;
	ResourceHandler resourceHandler = ResourceHandler.getInstance();
	Connection con = resourceHandler.getConnection();

	public static void callErrorBox() {
		MessageBox errorBox = new MessageBox(new Shell(Display.getCurrent()),
				SWT.ERROR);
		errorBox.setMessage("An error has occured while accessing the database\nIf you are a developer, you can check the error log\nPlease restart and try again"
				+ "\nIf the problem persists, please contact the developers\n");
		errorBox.setText("ERROR");
		errorBox.open();
	}

	public boolean isLoginValid(String uname, String passwd) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM login where uname like" + "'"
					+ uname + "'" + "and passwd like" + "'" + passwd + "'");
			while (rs.next()) {
				return true;
			}

		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
			ErrorWriter.writeErrorLog(e);
		} finally {
			try {
				rs.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
				ErrorWriter.writeErrorLog(e);
			}
		}
		return false;
	}

	public boolean isDBAdmin(String passwd) {
		try {
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT * FROM adminpasswd where passwd like "
							+ "'" + passwd + "'");
			while (rs.next()) {
				return true;
			}

		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
			ErrorWriter.writeErrorLog(e);
		} finally {
			try {
				rs.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
				ErrorWriter.writeErrorLog(e);
			}
		}
		return false;
	}

	public void changeAdminPasswd(String newPasswd) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("delete from adminpasswd");
			rs1 = stmt.executeQuery("Insert into adminpasswd values(" + "'"
					+ newPasswd + "')");

		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
			ErrorWriter.writeErrorLog(e);
		} finally {
			try {
				rs.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
				ErrorWriter.writeErrorLog(e);
			}
		}

	}

	/* Show Nurse info */
	public ArrayList<String[]> getNurseInfo() {

		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT AID,FName,Lname,Gender,address,contactno FROM attendant order by AID");
			while (rs.next()) {
				String[] string = new String[7];
				string[0] = rs.getString(1);
				string[1] = rs.getString(2);
				string[2] = rs.getString(3);
				string[3] = rs.getString(4);
				string[5] = rs.getString(5);
				string[4] = rs.getLong(6) + "";

				list.add(string);
			}
			for (int i = 0; i < list.size(); i++) {
				String[] string = list.get(i);
				string[6] = " ";
				rs1 = stmt
						.executeQuery("SELECT BNO FROM Bed_attendant where AID like"
								+ "'" + string[0] + "'");
				while (rs1.next()) {
					if (string[6] == " ")
						string[6] = rs1.getString(1);
					else
						string[6] += "," + rs1.getString(1);
				}
			}
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
			ErrorWriter.writeErrorLog(e);
		} finally {
			try {
				rs.close();
				rs1.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
				ErrorWriter.writeErrorLog(e);
			}
		}
		return list;
	}

	// Set nurse info

	public void setNurseInfo(String AID, String Fname, String Lname,
			String Gender, String Address, long Contactno) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("INSERT INTO attendant values (" + "'" + AID
					+ "'" + "," + "'" + Fname + "'" + "," + "'" + Lname + "'"
					+ "," + "'" + Gender + "'" + "," + Contactno + "," + "'"
					+ Address + "'" + ")");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// delete nurse info
	public void deleteNurseInfo(String AID) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("DELETE FROM ATTENDANT WHERE AID like "
					+ "'" + AID + "'");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// Show bed info
	public ArrayList<String[]> getBedInfo() {

		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Bed order by BNO");
			while (rs.next()) {
				String[] string = new String[5];
				string[0] = rs.getString(1);
				string[1] = rs.getString(2);
				string[4] = rs.getLong(3) + "";
				list.add(string);
			}

			for (int i = 0; i < list.size(); i++) {
				String[] string = list.get(i);
				string[2] = " ";
				rs1 = stmt
						.executeQuery("SELECT AID FROM Bed_attendant where BNO like"
								+ "'" + string[0] + "'");
				while (rs1.next()) {
					if (string[2] == " ")
						string[2] = rs1.getString(1);
					else
						string[2] += "," + rs1.getString(1);
				}
			}
			for (int i = 0; i < list.size(); i++) {
				String[] string = list.get(i);
				string[3] = "Vacant";
				rs2 = stmt
						.executeQuery("Select BNO FROM INPATIENT where BNO like"
								+ "'" + string[0] + "'");
				while (rs2.next()) {
					string[3] = "Occupied";
				}

			}
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs1.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
		return list;
	}

	// set bed info
	public void setBedInfo(String BNO, String BedType, String rent, String[] AID) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("INSERT INTO bed values (" + "'" + BNO + "'"
					+ "," + "'" + BedType + "'" + rent + ")");
			for (int i = 0; i < AID.length; i++) {
				rs1 = stmt.executeQuery("INSERT INTO BED_ATTENDANT values("
						+ "'" + BNO + "'" + "," + "'" + AID[i] + "'" + ")");
			}
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// Delete Bed info
	public void deleteBedInfo(String BNO) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("DELETE FROM bed WHERE BNO like " + "'"
					+ BNO + "'");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// Get Patient info
	public ArrayList<String[]> getPatientInfo() {

		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM inpatient order by PID");
			while (rs.next()) {
				String[] string = new String[12];
				for (int i = 0; i < string.length; i++) {
					string[i] = "----";

				}
				string[0] = rs.getString(1);
				string[1] = rs.getString(2);
				string[2] = rs.getString(3);
				string[3] = rs.getLong(4) + "";
				string[4] = rs.getString(5);
				string[5] = rs.getDate(6) + "";
				string[6] = rs.getDate(7) + "";
				string[7] = rs.getString(10);
				string[9] = rs.getString(8);
				string[11] = rs.getString(9);
				string[8] = rs.getString(11);

				list.add(string);
			}
			for (int i = 0; i < list.size(); i++) {
				String[] string = list.get(i);
				rs1 = stmt
						.executeQuery("SELECT DID FROM In_Doctor where  PID like"
								+ "'" + string[0] + "'");
				while (rs1.next()) {
					if (string[10] == "----")
						string[10] = rs1.getString(1);
					else
						string[10] += "," + rs1.getString(1);
				}
			}
			rs = stmt.executeQuery("SELECT * FROM outpatient order by PID");
			while (rs.next()) {
				String[] string1 = new String[12];
				for (int i = 0; i < string1.length; i++) {
					string1[i] = "----";

				}
				string1[0] = rs.getString(1);
				string1[1] = rs.getString(2);
				string1[2] = rs.getString(3);
				string1[3] = rs.getLong(4) + "";
				string1[4] = rs.getString(5);
				string1[5] = rs.getDate(6) + "";
				string1[7] = rs.getString(7);
				string1[8] = rs.getString(8);

				list.add(string1);
			}

			for (int i = 0; i < list.size(); i++) {
				String[] string = list.get(i);
				rs1 = stmt
						.executeQuery("SELECT DID FROM Out_appointment where  PID like"
								+ "'" + string[0] + "'");
				while (rs1.next()) {
					if (string[10] == "----")
						string[10] = rs1.getString(1);
					else
						string[10] += "," + rs1.getString(1);
				}
			}
			for (int i = 0; i < list.size(); i++) {
				String[] string = list.get(i);
				for (int j = 0; j < string.length; j++)
					if (string[j] == null)
						string[j] = "----";
			}

		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs1.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
		return list;
	}

	// Set patient details
	public void setPatientInfo(String PID, String Fname, String Lname,
			long Contactno, String Gender, String DOB, String DOA,
			String Cardno, String Address, String Diagnosis, String DID,
			String Bedno) {
		if (PID.startsWith("I")) {
			try

			{
				stmt = con.createStatement();
				rs = stmt.executeQuery("INSERT INTO Inpatient values (" + "'"
						+ PID + "'" + "," + "'" + Fname + "'" + "," + "'"
						+ Lname + "'" + "," + Contactno + "," + "'" + Gender
						+ "'" + "," + "'" + DOB + "'" + "," + "'" + DOA + "'"
						+ "," + "'" + Diagnosis + "'" + "," + "'" + Bedno + "'"
						+ "," + "'" + Cardno + "'" + "," + "'" + Address + "'"
						+ ")");
				rs1 = stmt.executeQuery("Insert into in_doctor values(" + "'"
						+ PID + "'" + "," + "'" + DID + "'" + ")");
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
				} catch (NullPointerException a) {
					a.printStackTrace();
					ErrorWriter.writeErrorLog(a);
				} catch (Exception e) {
					callErrorBox();
					ErrorWriter.writeErrorLog(e);
					e.printStackTrace();
				}
			}
		} else {
			try

			{
				stmt = con.createStatement();
				rs1 = stmt.executeQuery("INSERT INTO outpatient values (" + "'"
						+ PID + "'" + "," + "'" + Fname + "'" + "," + "'"
						+ Lname + "'" + "," + Contactno + "," + "'" + Gender
						+ "'" + "," + "'" + DOB + "'" + "," + "'" + Cardno
						+ "'" + "," + "'" + Address + "'" + ")");
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			} finally {
				try {
					rs1.close();
					stmt.close();
				} catch (NullPointerException a) {
					a.printStackTrace();
					ErrorWriter.writeErrorLog(a);
				} catch (Exception e) {
					callErrorBox();
					ErrorWriter.writeErrorLog(e);
					e.printStackTrace();
				}
			}
		}
	}

	// Delete Patient
	public void deletePaientInfo(String PID) {
		if (PID.startsWith("O")) {
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery("DELETE FROM OUTPATIENT WHERE PID like "
						+ "'" + PID + "'");
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
				} catch (NullPointerException a) {
					a.printStackTrace();
					ErrorWriter.writeErrorLog(a);
				} catch (Exception e) {
					callErrorBox();
					ErrorWriter.writeErrorLog(e);
					e.printStackTrace();
				}
			}
		}

		else {
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery("DELETE FROM INPATIENT WHERE PID like "
						+ "'" + PID + "'");
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
				} catch (NullPointerException a) {
					a.printStackTrace();
					ErrorWriter.writeErrorLog(a);
				} catch (Exception e) {
					callErrorBox();
					ErrorWriter.writeErrorLog(e);
					e.printStackTrace();
				}
			}

		}
	}

	// show doc info
	public ArrayList<String[]> getDoctorInfo() {

		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM DOCTOR order by DID");
			while (rs.next()) {
				long a, b, c;
				String[] string = new String[10];
				string[0] = rs.getString(1);
				string[1] = rs.getString(2);
				string[2] = rs.getString(3);
				string[3] = rs.getString(4);
				string[4] = rs.getString(5);
				string[5] = rs.getLong(6) + "";
				string[6] = rs.getString(7);
				a = rs.getLong(8);
				b = a / 100;
				c = a % 100;
				string[7] = b + ":" + c + "";
				a = rs.getLong(9);
				b = a / 100;
				c = a % 100;
				string[8] = b + ":" + c + "";
				string[9] = rs.getString(10);

				list.add(string);
			}
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
		return list;
	}

	// set doc info
	public void setDoctorInfo(String DID, String Fname, String Lname,
			String Qualification, String Gender, long Contactno, String DNo,
			String StartTime, String EndTime, String Address) {
		try {
			long a, b;
			a = Long.parseLong(StartTime.split(":")[0]) * 100
					+ Long.parseLong(StartTime.split(":")[1]);
			b = Long.parseLong(EndTime.split(":")[0]) * 100
					+ Long.parseLong(EndTime.split(":")[1]);
			stmt = con.createStatement();
			rs = stmt.executeQuery("INSERT INTO DOCTOR values (" + "'" + DID
					+ "'" + "," + "'" + Fname + "'" + "," + "'" + Lname + "'"
					+ "," + "'" + Qualification + "'" + "," + "'" + Gender
					+ "'" + "," + Contactno + "," + "'" + DNo + "'" + "," + a
					+ "," + b + "," + "'" + Address + "'" + ")");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// Delete doc info
	public void deleteDoctorInfo(String DID) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("DELETE FROM DOCTOR WHERE DID like " + "'"
					+ DID + "'");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// Check Availability
	public ArrayList<String[]> showDoctorAppoitment(String DID, String ADATE) {

		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT DID,FNAME,LNAME,STARTTIME,ENDTIME,ATIME FROM DOCTOR NATURAL JOIN OUT_APPOINTMENT where ADATE = "
							+ "'"
							+ ADATE
							+ "'"
							+ " and DNO in (select DNO from DOCTOR where DID LIKE"
							+ "'" + DID + "'" + ")order by DID ");// and ADATE
																	// LIKE
																	// " + "'"
			// + ADATE + "'");
			while (rs.next()) {
				long a, b, c;
				String[] string = new String[6];
				string[0] = rs.getString(1);
				string[1] = rs.getString(2);
				string[2] = rs.getString(3);

				a = rs.getLong(4);
				b = a / 100;
				c = a % 100;
				string[3] = b + ":" + c + "";

				a = rs.getLong(5);
				b = a / 100;
				c = a % 100;
				string[4] = b + ":" + c + "";

				a = rs.getLong(6);
				b = a / 100;
				c = a % 100;
				string[5] = b + ":" + c + "";

				list.add(string);
			}
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
		System.out.println("QH " + list.size());
		return list;
	}

	// Add ament
	public void addAppointment(String DID, String PID, String ADATE,
			String ATIME) {
		try {
			long a;
			a = Long.parseLong(ATIME.split(":")[0]) * 100
					+ Long.parseLong(ATIME.split(":")[1]);
			stmt = con.createStatement();
			System.out.println(DID + " : " + PID + " : " + ADATE + " : " + a);
			rs = stmt.executeQuery("INSERT INTO OUT_APPOINTMENT values (" + "'"
					+ PID + "'" + "," + "'" + DID + "'" + "," + "'" + ADATE
					+ "'" + "," + a + ")");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// Delete Healthcard
	public void deleteHealthcard(String cardno) {
		try {
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("DELETE FROM HEALTHCARD_HOLDER WHERE cardno LIKE"
							+ "'" + cardno + "'");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// Show Department
	public ArrayList<String[]> getDeptInfo() {

		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Department order by DNO");
			while (rs.next()) {
				String[] string = new String[3];
				string[0] = rs.getString(1);
				string[1] = rs.getString(2);
				string[2] = rs.getString(3);
				list.add(string);
			}
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
		return list;
	}

	// Add department
	public void setDeptInfo(String DNO, String dname, String HODID) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("INSERT INTO department values (" + "'"
					+ DNO + "'" + "," + "'" + dname + "'" + "," + "'" + HODID
					+ "'" + ")");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
				ErrorWriter.writeErrorLog(e);
			}
		}
	}

	// Delete department
	public void deleteDeptInfo(String DNO) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("Delete from department where dno like "
					+ "'" + DNO + "'");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// Edit patient service
	public void editServiceInfo(String PID, String[] service) {
		if (PID.startsWith("I")) {
			try

			{
				stmt = con.createStatement();
				rs1 = stmt.executeQuery("delete from in_service where PID like"
						+ "'" + PID + "'");

				for (int i = 0; i < service.length; i++)
					rs2 = stmt.executeQuery("INSERT INTO IN_SERVICE VALUES("
							+ "'" + PID + "'" + "," + "'" + service[i] + "'"
							+ ")");
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			} finally {
				try {
					rs2.close();
					rs1.close();
					stmt.close();
				} catch (NullPointerException a) {
					a.printStackTrace();
					ErrorWriter.writeErrorLog(a);
				} catch (Exception e) {
					callErrorBox();
					ErrorWriter.writeErrorLog(e);
					e.printStackTrace();
				}
			}
		} else {
			try

			{
				stmt = con.createStatement();
				rs1 = stmt
						.executeQuery("delete from out_service where PID like"
								+ "'" + PID + "'");

				for (int i = 0; i < service.length; i++) {
					if (service[i].equals("") || service[i] == null
							|| service[i].equals(" "))
						continue;
					rs2 = stmt.executeQuery("INSERT INTO out_SERVICE VALUES("
							+ "'" + PID + "'" + "," + "'" + service[i] + "'"
							+ ")");
				}
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			} finally {
				try {
					rs1.close();
					stmt.close();
				} catch (NullPointerException a) {
					a.printStackTrace();
					ErrorWriter.writeErrorLog(a);
				} catch (Exception e) {
					callErrorBox();
					ErrorWriter.writeErrorLog(e);
					e.printStackTrace();
				}
			}
		}
	}

	// sHOW CURRENT SERVICES
	public String[] showCurrentServices(String PID) {
		String[] s = new String[50];
		int i = 0;
		if (PID.startsWith("I")) {
			try {
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("SELECT SNAME FROM IN_SERVICE WHERE PID LIKE"
								+ "'" + PID + "'" + "order by sname");
				while (rs.next()) {
					s[i] = rs.getString(1);
					i++;
				}
				ArrayList<String> list = new ArrayList<String>();

				for (String a : s) {
					if (a != null && a.length() > 0) {
						list.add(a);
					}
				}

				s = list.toArray(new String[list.size()]);
			}

			catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
				} catch (NullPointerException a) {
					a.printStackTrace();
					ErrorWriter.writeErrorLog(a);
				} catch (Exception e) {
					callErrorBox();
					ErrorWriter.writeErrorLog(e);
					e.printStackTrace();
				}
			}
		}

		else {
			try {
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("SELECT SNAME FROM OUT_SERVICE WHERE PID LIKE"
								+ "'" + PID + "'");
				while (rs.next()) {
					s[i] = rs.getString(1);
					i++;
				}
				ArrayList<String> list = new ArrayList<String>();

				for (String a : s) {
					if (a != null && a.length() > 0) {
						list.add(a);
					}
				}

				s = list.toArray(new String[list.size()]);
			}

			catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			} finally {
				try {
					rs1.close();
					stmt.close();
				} catch (NullPointerException a) {
					a.printStackTrace();
					ErrorWriter.writeErrorLog(a);
				} catch (Exception e) {
					callErrorBox();
					ErrorWriter.writeErrorLog(e);
					e.printStackTrace();
				}
			}
		}
		return s;
	}

	// Add service
	public void addServices(String sname, float scost) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("INSERT INTO services values (" + "'"
					+ sname + "'" + "," + scost + ")");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// Show Services
	public ArrayList<String[]> getServices() {

		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Services order by Sname");
			while (rs.next()) {
				String[] string = new String[2];
				string[0] = rs.getString(1);
				string[1] = rs.getFloat(2) + "";

				list.add(string);
			}
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
		return list;
	}

	// Delete service
	public void deleteServices(String sname) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("DELETE FROM SERVICES WHERE SNAME LIKE"
					+ "'" + sname + "'");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// aDD HEALTHCARD
	public void setHealthCard(String CARDNO, String fname, String lname,
			String gender, String DOB, long contactno, String Address,
			float coverage) {
		try

		{
			stmt = con.createStatement();
			rs1 = stmt.executeQuery("INSERT INTO HEALTHCARD_HOLDER values ("
					+ "'"
					+ CARDNO
					+ "'"
					+ ","
					+ "'"
					+ fname
					+ "'"
					+ ","
					+ "'"
					+ lname
					+ "'"
					+ ","
					+ "'"
					+ gender
					+ "'"
					+ ","
					+ "'"
					+ DOB
					+ "'"
					+ ","
					+ "'"
					+ contactno
					+ "'"
					+ ","
					+ "'"
					+ Address
					+ "'" + "," + coverage + ")");
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs1.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
	}

	// Show healthcard holders
	public ArrayList<String[]> getHealthcard() {

		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT * FROM healthcard_holder order by cardno");
			while (rs.next()) {
				String[] string = new String[8];
				for (int i = 0; i < string.length; i++) {
					string[i] = "----";

				}
				string[0] = rs.getString(1);
				string[1] = rs.getString(2);
				string[2] = rs.getString(3);
				string[3] = rs.getString(4) + "";
				string[4] = rs.getDate(5) + "";
				string[5] = rs.getLong(6) + "";
				string[6] = rs.getString(7);
				string[7] = rs.getLong(8) + "";
				list.add(string);
			}
		} catch (NullPointerException a) {
			a.printStackTrace();
			ErrorWriter.writeErrorLog(a);
		} catch (Exception e) {
			callErrorBox();
			ErrorWriter.writeErrorLog(e);
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();

			} catch (NullPointerException a) {
				a.printStackTrace();
				ErrorWriter.writeErrorLog(a);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			}
		}
		return list;
	}

	// Discharge Summary

	public String[] dischargeSummary(String PID) {
		String a[] = new String[50];
		if (PID.startsWith("I")) {
			try

			{
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("Select Fname,lname,gender,contactno,address,bno,DOB,DOA,cardno from Inpatient where PID like"
								+ "'" + PID + "'");
				while (rs.next()) {
					a[0] = rs.getString(1);
					a[1] = rs.getString(2);
					a[2] = rs.getString(3);
					a[3] = rs.getLong(4) + "";
					a[4] = rs.getString(5);
					a[5] = rs.getString(6);
					a[6] = rs.getDate(7) + "";
					a[7] = rs.getDate(8) + "";
					a[8] = rs.getString(9) == null ? "----" : rs.getString(9);
				}
				a[9] = "---";
				rs1 = stmt
						.executeQuery("Select Fname,Lname from in_doctor natural join doctor where pid like"
								+ "'" + PID + "'");
				while (rs1.next()) {
					a[9] = "Dr. " + rs1.getString(1) + " " + rs1.getString(2);
				}
				float x = 0, y = 0;
				rs5 = stmt
						.executeQuery("select to_date(sysdate,'dd-mm-yy') - to_date(doa,'dd-mm-yy') from inpatient where pid like"
								+ "'" + PID + "'");
				while (rs5.next()) {
					y = rs5.getFloat(1);
					y += 1;
				}
				rs4 = stmt
						.executeQuery("Select rent from BED where bno like (select bno from inpatient where pid like"
								+ "'" + PID + "'" + ")");
				while (rs4.next()) {

					x = rs4.getFloat(1);
					x *= y;
					a[10] = "Room Rent Rs. " + x + "/-";

				}
				rs2 = stmt
						.executeQuery("Select sname,scost from in_service natural join services where pid like"
								+ "'" + PID + "'");
				int i = 11;
				while (rs2.next()) {
					a[i] = rs2.getString(1) + "   " + "Rs. " + rs2.getString(2)
							+ "/-";
					i++;
				}

				i++;
				rs3 = stmt
						.executeQuery("Select sum(scost) from in_service natural join services where pid like"
								+ "'" + PID + "'");

				while (rs3.next()) {
					a[i] = rs3.getLong(1) + "";
					x += Float.parseFloat(a[i]);
					if (a[8] != null) {
						rs6 = stmt
								.executeQuery("Select discount from healthcard_holder where cardno like "
										+ "'" + a[8] + "'");
						while (rs6.next()) {
							x = (x * (100 - rs6.getFloat(1)));
							a[i - 1] = "Discount  " + rs.getFloat(1) + "%";
						}
					}
					a[i] = x + "";
				}

			} catch (NullPointerException c) {
				c.printStackTrace();
				ErrorWriter.writeErrorLog(c);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					rs1.close();
					rs2.close();
					rs3.close();
					rs4.close();
					rs6.close();
					stmt.close();
				} catch (NullPointerException c) {
					c.printStackTrace();
					ErrorWriter.writeErrorLog(c);
				} catch (Exception e) {
					callErrorBox();
					ErrorWriter.writeErrorLog(e);
					e.printStackTrace();
				}
			}
		} else if (PID.startsWith("O")) {
			try

			{
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("Select Fname,lname,gender,contactno,address,DOB,cardno from Outpatient where pid like "
								+ "'" + PID + "'");
				while (rs.next()) {
					a[0] = rs.getString(1);
					a[1] = rs.getString(2);
					a[2] = rs.getString(3);
					a[3] = rs.getLong(4) + "";
					a[4] = rs.getString(5);
					a[5] = "----";
					a[6] = rs.getDate(6) + "";
					a[7] = "----";
					a[8] = (rs.getString(7) == null ? "----" : rs.getString(7));
				}

				rs1 = stmt
						.executeQuery("Select Fname,Lname from out_appointment natural join doctor where pid like"
								+ "'" + PID + "'");
				while (rs1.next()) {
					a[9] = "Dr. " + rs1.getString(1) + " " + rs1.getString(2);
				}
				a[9] = a[9] == null ? "----" : a[9];
				rs2 = stmt
						.executeQuery("Select sname,scost from out_service natural join services where pid like"
								+ "'" + PID + "'");
				int i = 10;
				while (rs2.next()) {
					a[i] = rs2.getString(1) + "   " + "Rs. " + rs2.getString(2)
							+ "/-";
					i++;
				}

				i++;

				float x = 0;
				rs3 = stmt
						.executeQuery("Select sum(scost) from out_service natural join services where pid like"
								+ "'" + PID + "'");
				while (rs3.next()) {
					x = rs3.getFloat(1);
					if (a[8] != null) {
						rs6 = stmt
								.executeQuery("Select discount from healthcard_holder where cardno like "
										+ "'" + a[8] + "'");
						while (rs6.next()) {
							x = (x * (100 - rs6.getFloat(1)));
							a[i - 1] = "Discount  " + rs.getFloat(1) + "%";
						}
					}
					a[i] = x + "";
				}

			} catch (NullPointerException c) {
				c.printStackTrace();
				ErrorWriter.writeErrorLog(c);
			} catch (Exception e) {
				callErrorBox();
				ErrorWriter.writeErrorLog(e);
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					rs1.close();
					rs2.close();
					rs3.close();
					rs6.close();
					stmt.close();
				} catch (NullPointerException c) {
					c.printStackTrace();
					ErrorWriter.writeErrorLog(c);
				} catch (Exception e) {
					callErrorBox();
					ErrorWriter.writeErrorLog(e);
					e.printStackTrace();
				}
			}
		}
		ArrayList<String> list = new ArrayList<>();
		for (String s : a) {
			if (s != null && s.length() > 0) {
				list.add(s);
			}
		}

		a = list.toArray(new String[list.size()]);
		return a;
	}

	// Previous patient id
	public String[] previousPID() {
		String[] s = new String[2];
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select max(pid) from inpatient");
			while (rs.next()) {
				s[0] = rs.getString(1);
			}
			rs1 = stmt.executeQuery("select max(pid) from outpatient");
			while (rs1.next()) {
				s[1] = rs1.getString(1);
			}
			if (s[0] == null)
				s[0] = " ";
			if (s[1] == null)
				s[1] = " ";
		} catch (NullPointerException a) {
			a.printStackTrace();
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs1.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
			}
		}
		return s;
	}

	// Previous Healthcard no
	public String healthcardno() {
		String s = " ";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select max(cardno) from healthcard_holder");
			while (rs.next()) {
				s = rs.getString(1);
			}

		} catch (NullPointerException a) {
			a.printStackTrace();
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
			}
		}
		return s;
	}

	// Update nurse
	public void updateNurse(String AID, String fname, String lname,
			String gender, String address, long contactno) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("update attendant set fname = " + "'"
					+ fname + "'" + " , lname = " + "'" + lname + "'"
					+ ", gender = " + "'" + gender + "'" + ", address = " + "'"
					+ address + "'" + ", contactno = " + contactno
					+ "where aid like" + "'" + AID + "'");
		} catch (NullPointerException a) {
			a.printStackTrace();
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
			}
		}
	}

	// Update doctor
	public void updateDoctor(String DID, String Fname, String Lname,
			String Qualification, String Gender, long Contactno, String DNo,
			String StartTime, String EndTime, String Address) {
		try {
			long a, b;
			a = Long.parseLong(StartTime.split(":")[0]) * 100
					+ Long.parseLong(StartTime.split(":")[1]);
			b = Long.parseLong(EndTime.split(":")[0]) * 100
					+ Long.parseLong(EndTime.split(":")[1]);
			stmt = con.createStatement();
			rs = stmt.executeQuery("Update doctor set Fname =" + "'" + Fname
					+ "'" + ",Lname = " + "'" + Lname + "'"
					+ ", qualification = " + "'" + Qualification + "'"
					+ ", Gender = " + "'" + Gender + "'" + ", contactno = "
					+ Contactno + ", dno = " + "'" + DNo + "'"
					+ ", starttime = " + a + ", endtime = " + b
					+ ", Address = " + "'" + Address + "' where did like "
					+ "'" + DID + "'");
		} catch (NullPointerException a) {
			a.printStackTrace();
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
			}
		}
	}

	// Update Department
	public void updateDepartment(String DNO, String Dname, String HODID) {
		try {
			rs = stmt.executeQuery("update table DEPARTMENT set dname = " + "'"
					+ Dname + "'" + ", hodid = " + "'" + HODID + "'"
					+ "where dno like" + "'" + DNO + "'");
		} catch (NullPointerException a) {
			a.printStackTrace();
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
			}
		}
	}

	// Update BED
	public void updateBed(String BNO, String BedType, String rent, String[] AID) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("update bed set wardtype = " + "'" + BedType
					+ "'" + " , rent= " + rent + " where bno like " + "'" + BNO
					+ "'");
			rs1 = stmt.executeQuery("delete from bed_attendant where bno like "
					+ "'" + BNO + "'");
			for (int i = 0; i < AID.length; i++) {
				rs2 = stmt.executeQuery("INSERT INTO BED_ATTENDANT values("
						+ "'" + BNO + "'" + "," + "'" + AID[i] + "'" + ")");
			}
		}

		catch (NullPointerException a) {
			a.printStackTrace();
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs1.close();
				rs2.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
			}
		}
	}

	// Update Healthcard
	public void updateHealthcard(String cardno, String fname, String lname,
			String gender, String dob, long contactno, String address,
			float coverage) {
		try {
			stmt = con.createStatement();
			System.out.println(cardno + " " + fname + " " + lname + " "
					+ gender + " " + dob + " " + contactno + " " + address
					+ " " + coverage);
			rs = stmt.executeQuery("update healthcard_holder set fname = "
					+ "'"
					+ fname
					+ "'"
					+ " , lname = "
					+ "'"
					+ lname
					+ "'"
					+ ", gender = "
					+ "'"
					+ gender
					+ "'"
					+ ", dob = "
					+ "'"
					+ dob
					+ "'"
					+ ", contactno = "
					+ contactno
					+ ", address = "
					+ "'"
					+ address
					+ "'"
					+ ",coverage = "
					+ coverage
					+ " where cardno like " + "'" + cardno + "'");
		} catch (NullPointerException a) {
			a.printStackTrace();
		} catch (Exception e) {
			callErrorBox();
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (NullPointerException a) {
				a.printStackTrace();
			} catch (Exception e) {
				callErrorBox();
				e.printStackTrace();
			}
		}
	}
}