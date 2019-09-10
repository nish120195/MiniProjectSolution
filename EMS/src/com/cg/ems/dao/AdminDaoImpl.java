package com.cg.ems.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.cg.ems.dto.Department;
import com.cg.ems.dto.Employee;
import com.cg.ems.dto.GradeMaster;
import com.cg.ems.exception.EmployeeException;
import com.cg.ems.util.DBUtil;
import com.cg.ems.util.MyStringDateUtil;

public class AdminDaoImpl implements AdminDao
{
	Connection con=null;
	Statement st=null;
	PreparedStatement pst=null;
	ResultSet rs=null;

	public void connect()
	{
		try 
		{
			con=DBUtil.getCon();
		} 
		catch (ClassNotFoundException | SQLException | IOException e) 
		{
			
			e.printStackTrace();
		}
	}
	@Override
	public int addEmployee(Employee emp) throws EmployeeException
	{
		connect();
		int dataInserted=0;
		try 
		{
			pst=con.prepareStatement(QueryMapper.EMP_INSERT_QRY);
			pst.setString(1, emp.getEmpID());
			pst.setString(2,emp.getEmpFirstName());
			pst.setString(3, emp.getEmpLastName());
			pst.setDate(4, MyStringDateUtil .fromLocalToSqlDate(emp.getEmpDateofBirth()));
			pst.setDate(5, MyStringDateUtil .fromLocalToSqlDate(emp.getEmpDateofJoining()));
			//pst.setString(6, emp.getEmpDepartment());
			pst.setInt(6, emp.getEmpDepartmentId());
			pst.setString(7, emp.getEmpGrade());
			pst.setString(8, emp.getEmpDesignation());
			pst.setInt(9, emp.getEmpBasicSal());
			pst.setString(10, emp.getEmpGender());
			pst.setString(11, emp.getEmpMaritalStatus());
			pst.setString(12, emp.getEmpHomeAddress());
			pst.setLong(13, emp.getEmpContactNum());
			pst.setString(14, emp.getMgrId());			
			dataInserted=pst.executeUpdate();			
		} 
		catch (SQLException e) 
		{
			throw new EmployeeException(e.getMessage());
		}
		return dataInserted;
	}

	@Override
	public int[] salaryBracket(String id) throws EmployeeException 
	{
		connect();
		 int[] sal=new int[2];
		try
		{
			pst=con.prepareStatement(QueryMapper.GET_EMP_SALARY);
			pst.setString(1, id);
			rs=pst.executeQuery();
			while(rs.next()) 
			{
				sal[0]=rs.getInt(1);
				sal[1]=rs.getInt(2);
			}
		}
		catch(SQLException e)
		{
			throw new EmployeeException(e.getMessage());
		}
		return sal;
	}

	@Override
	public String getDepartment(int id) throws EmployeeException 
	{
		connect();
		String empDepartment=null;
		try
		{
			pst=con.prepareStatement(QueryMapper.GET_EMP_DEPARTMENT);
			pst.setInt(1, id);
			rs=pst.executeQuery();
			while(rs.next()) 
			{
				empDepartment=rs.getString(1);
			}
		}
		catch(SQLException e)
		{
			throw new EmployeeException(e.getMessage());
		}
		return empDepartment;
	}
	@Override
	public ArrayList<Department> displayDepartment() throws EmployeeException 
	{
		connect();
		ArrayList<Department>DepartmentList=new ArrayList<Department>();
		try
		{
			pst=con.prepareStatement(QueryMapper.GET_DEPARTMENT);
			rs=pst.executeQuery();
			Department dept;
			while(rs.next())
			{
				dept=new Department(rs.getString(2),rs.getInt(1));
				DepartmentList.add(dept);
			}
		}
		catch(SQLException e)
		{
			throw new EmployeeException(e.getMessage());
		}
		
		return DepartmentList;
	}
	@Override
	public ArrayList<GradeMaster> getGradeCodes() throws EmployeeException 
	{
		connect();
		ArrayList<GradeMaster> grade=new ArrayList<GradeMaster>();
		try
		{
			pst=con.prepareStatement(QueryMapper.GET_GRADECODE);
			rs=pst.executeQuery();
			GradeMaster details;
			while(rs.next())
			{
				details=new GradeMaster(rs.getString(1),rs.getInt(3),rs.getInt(4));
				grade.add(details);
			}
		}
		catch(SQLException e)
		{
			throw new EmployeeException(e.getMessage());
			
		}
		return grade;
	}
	@Override
	public ArrayList<Employee> displayAllEmployee() throws EmployeeException 
	{
		ArrayList<Employee>list=new ArrayList<Employee>();
		connect();
		try
		{
			st=con.createStatement();
			rs=st.executeQuery(QueryMapper.GET_ALL_EMPLOYEE);
			while(rs.next())
			{						
				Employee emp=new Employee(rs.getString(1),rs.getString(2),rs.getString(3),MyStringDateUtil.fromSqlToLocalDate(rs.getDate(4)),
			MyStringDateUtil.fromSqlToLocalDate(rs.getDate(5)),rs.getInt(6),rs.getString(7),rs.getString(8),
			rs.getInt(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getLong(13),rs.getString(14));
				list.add(emp);
				//System.out.println(emp);
			}
		}
		catch(SQLException e)
		{
			throw new EmployeeException(e.getMessage());
			
		}
		return list;
	}
	@Override
	public String updateEmployee(Employee e) throws EmployeeException {
		String ans=null;
		connect();
		
		try {
			st=con.createStatement();
			rs=st.executeQuery(QueryMapper.UPDATE_Employee);
			
			pst.setString(1,e.getEmpFirstName());
			pst.setString(2, e.getEmpLastName());
			pst.setInt(3,e.getEmpDepartmentId());
			pst.setString(4,e.getEmpGrade());
			pst.setString(5,e.getEmpDesignation());
			pst.setInt(6, e.getEmpBasicSal());
			pst.setString(7,e.getEmpGender());
			pst.setString(8, e.getEmpGender());
			pst.setString(9, e.getEmpMaritalStatus());
			pst.setString(10,e.getEmpHomeAddress());
			pst.setLong(11,e.getEmpContactNum());
			pst.setString(12,e.getEmpID());
			
			
	}catch(Exception e1)
		{
		  }
		return "";	
		}
	
	}
	
	


