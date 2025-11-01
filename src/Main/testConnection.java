/*
 * @ (#) testConnection.java   1.0     Oct 31, 2025
 *
 * Copyright (c) 2025 IUH.
 * All rights reserved.
 */

package Main;
/*
* @description
* @author: Van Long
* @date: Oct 31, 2025
* @version: 1.0
*/

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import DAO.HoaDon_DAO;
import Entity.HoaDon;

public class testConnection {
	public static void main(String[] args) {
		Connection con = ConnectDB.getInstance().getConnection();
		if (con != null) {
			HoaDon_DAO spDAO = new HoaDon_DAO();
			System.out.println("Danh sách sản phẩm");
			List<HoaDon> dsSP = new ArrayList<HoaDon>();
			dsSP = spDAO.layTatCa();
			System.out.printf("%-10s %-25s %-10s %-10s %-10s %-5s %-10s\n", "Mã SP", "Tên SP", "ĐVT", "Giá", "Mô tả",
					"TT", "Loại");
			System.out.println("--------------------------------------------------------------");
			for (HoaDon sp : dsSP) {
				System.out.println(sp);
			}
		}
	}
}
