package twilight.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Yoshida
 * DbへのCRUDを扱う。
 * 初回のみ、TargetDataBeanにDB接続情報を渡してやる必要がある。
 * 以降はこのクラスをインスタンス化し、SQLをセットし、CRUD用のメソッドを実行すればよい。
 *
 */

public class DbProcess extends DbManager {

	private Connection con       = null;
	private PreparedStatement ps = null;

	private String sql = "";


	//コンストラクタ
	private DbProcess() throws SQLException {

		super();

	}


	/**
	 * DBからデータを取得する
	 * @param parameters
	 * @return
	 */
	public ResultSet getData(String[] parameters){

		ResultSet rs = null;

		try{

			this.con = this.getConnection();

			if(parameters == null){

				this.setPs();

			}else{

				this.setPs(parameters);

			}

			rs = this.getPs().executeQuery();

			close(rs);

		}catch(Exception e){

			close(rs);

		}

		return rs;

	}


	/**
	 * DBに対して登録・更新・削除を行う
	 */
	public int updateDb(ArrayList list){

		int resultCount = 0;

		try{

			this.con = this.getConnection();

			if(list == null){

				this.setPs();

			}else{

				this.setPs(list);

			}

			resultCount = this.getPs().executeUpdate();

			close();

		}catch(Exception e){

			close();

		}

		return resultCount;

	}



	/**
	 * 参照時、DB終了処理
	 */
	protected void close(ResultSet rs){

		this.close(this.ps);
		this.disConnect(this.con);
		this.close(rs);

	}


	/**
	 * 登録・更新・削除時、DB終了処理
	 */
	protected void close(){

		this.close(this.ps);
		this.disConnect(this.con);

	}


	private PreparedStatement getPs() {
		return ps;
	}


	/**
	 * プレースホルダーがない場合に使われる
	 * @param sql
	 * @throws SQLException
	 */
	private void setPs() throws SQLException{

		this.ps = this.con.prepareStatement(this.getSql());

	}


	/**
	 * プレースホルダーがある場合に使われる
	 * @param parameters
	 */
	private void setPs(String[] parameters) throws SQLException{

		this.ps = this.con.prepareStatement(this.getSql());

		for(int i=0; i<parameters.length; i++){

			this.ps.setString(i+1, parameters[i]);

		}

	}


	/**
	 * プレースホルダーがある場合に使われる。登録・更新・削除時に使用。
	 * @param parameters
	 */
	private void setPs(ArrayList list) throws SQLException{

		this.ps = this.con.prepareStatement(this.getSql());

		for(int i=0; i<list.size(); i++){

			this.ps.setString(i+1, (String)list.get(i));

		}

	}


	private String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}



}
