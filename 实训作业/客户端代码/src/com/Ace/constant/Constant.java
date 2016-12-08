package com.Ace.constant;

public class Constant {

	/**
	 * 根接口地址
	 */
	public static final String INTERFACE = "http://192.168.43.233:8080/DairyTally/";

	/**
	 * 1、 登录
	 */

	public static final String Login = INTERFACE + "Login";

	/**
	 * 2、注册
	 */

	public static final String Register = INTERFACE + "Register";

	/**
	 * 3、写日记
	 */

	public static final String WriteDairy = INTERFACE + "WriteDairy";

	/**
	 * 4、获取日记列表
	 */

	public static final String GetDariyList = INTERFACE + "GetDairy";

	/**
	 * 5、获取收支信息
	 */

	public static final String GetTally = INTERFACE + "GetTally";

	/**
	 * 6、收入支出
	 */

	public static final String InorOut = INTERFACE + "ChangeBalance";

	/**
	 * 7、修改密码
	 */

	public static final String ChangePassword = INTERFACE + "ChangePassword";

	/**
	 * 8、删除日记
	 */

	public static final String DeleteDairy = INTERFACE + "DeleteDairy";

	/**
	 * 9、删除收支详情
	 */

	public static final String DeleteTally = INTERFACE + "DeleteTally";
}
