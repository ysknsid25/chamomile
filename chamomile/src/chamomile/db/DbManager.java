package twilight.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.TargetDbBean;

/**
 *
 * @author Yoshida
 * DB操作における、共通処理的な部分を担う。
 *
 */

public class DbManager {

	//ドライバ
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	//URL localhost:1521/SJIS_DB
	private String url = "jdbc:oracle:thin:@";

	//ID
	private String id = "";

	//パスワード
	private String pass = "";

	//例外
	private SQLException sqlEx = new SQLException();


	//コンストラクタ
	protected DbManager() throws SQLException{

		if(TargetDbBean.sign.isEmpty()){

			throw sqlEx;

		}

		if(TargetDbBean.id.isEmpty()){

			throw sqlEx;

		}

		if(TargetDbBean.pass.isEmpty()){

			throw sqlEx;

		}

		this.url  = this.url + TargetDbBean.sign;
		this.id   = TargetDbBean.id;
		this.pass = TargetDbBean.pass;

	}


	//接続
	protected Connection getConnection() throws Exception{

		Class.forName(DRIVER);

		Connection con = DriverManager.getConnection(this.url, this.id, this.pass);

		return con;

	}


	//切断
	protected void disConnect(Connection con){

		try{

			con.close();

		}catch(SQLException e){
		}

	}


	//ステートメントをくろーずする
	protected void close(PreparedStatement ps){

		try{

			ps.close();

		}catch(SQLException e){
		}

	}


	//リザルトセットをクローズする
	protected void close(ResultSet rs){

		try{

			rs.close();

		}catch(SQLException e){
		}

	}

	/**
	 * getStringの値がnullの場合は空文字を、そうでないときはその値を返す。
	 * @param rowName
	 * @param rs
	 * @return
	 */
	protected String getString(String rowName, ResultSet rs){

		try{

			String rowValue = rs.getString(rowName);

			if(rowValue.isEmpty()){

				return "";

			}

			return rowValue;

		}catch(SQLException e){

			return "";

		}

	}

}
