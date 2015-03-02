package com.mastecom.wqms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WQMSLRPendingOrdersRecon {

	public static void main(String args[]) throws SQLException {
		SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String dateParse = sf.format(new Date());
		dateParse = dateParse.replace(" ","_").replace(":","_");

				Connection con = null;
				try {
					Class.forName("com.ibm.db2.jcc.DB2Driver");
					String url = "jdbc:db2://10.209.19.193:60025/WQMSDB";
					con = DriverManager.getConnection(url, "bpminst", "D@t@p0wer");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				ArrayList al = new ArrayList();
				try {
					File fNew = new File("C:/Users/Mastercom182/Desktop/WQMSLRPendingOrdersRecon_"+dateParse+".csv");
					if(!fNew.exists()){
						fNew.createNewFile();
					}
					BufferedWriter bw = new BufferedWriter(new FileWriter(fNew));
					String sel = "select wqms_id,order_id,Circuit_id,COPF_ID,wqms_step_name,order_status,status,created_date,order_Completion_date,modified_date,user_name,assigned_user,Customer_name,Billing_branch from wqms.wqms_order_details where source='Liferay' and (order_status='Pending FCOPF Vetting'  or WQMS_STEP_NAME='Perform FCOPF Vetting by L4' or STATUS='Commercial Vetting')and WQMS_STEP_NAME not in ('INSTANCE COMPLETE','Terminated in System')";
					PreparedStatement pst = con.prepareStatement(sel);
					ResultSet rst = pst.executeQuery();
					bw.write("wqms_id,order_id,Circuit_id,COPF_ID,wqms_step_name,order_status,status,created_date,order_Completion_date,modified_date,user_name,assigned_user,Customer_name,Billing_branch");
					bw.newLine();
					while (rst.next()) {
						al.add(rst.getString("wqms_id"));
						bw.write(rst.getString("wqms_id") + "," ) ;
						bw.write(rst.getString("order_id") + ",");
						bw.write(rst.getString("Circuit_id") + ",");
						bw.write(rst.getString("COPF_ID") + ",");
						bw.write(rst.getString("wqms_step_name") + ",");
						bw.write(rst.getString("order_status") + ",");
						bw.write(rst.getString("status") + ",");
						bw.write(rst.getString("created_date") + ",");
						bw.write(rst.getString("order_Completion_date") + ",");
						bw.write(rst.getString("modified_date") + ",");
						bw.write(rst.getString("user_name") + ",");
						bw.write(rst.getString("assigned_user") + ",");
						bw.write(rst.getString("Customer_name") + ",");
						bw.write(rst.getString("Billing_branch") + ",");
						bw.newLine();
									}
	
					bw.close();
				}

				catch (SQLException se) {
					se.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					con.close();

				}

			}

}
