package vn.vnpt.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;
import org.jdbi.v3.core.statement.Call;
import org.jdbi.v3.core.statement.OutParameters;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariDataSource;

import oracle.jdbc.OracleTypes;
import vn.vnpt.database.OracleParam.OracleDirect;
import vn.vnpt.message.Response;

@Service
public class DbHelper {
	private Boolean acceptnull = true;

	private final EntityManager entityManager;

	public DbHelper(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	private HikariDataSource getDataSource() {
		EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
		return (HikariDataSource) info.getDataSource();
	}

	public Response OpenRS(String p_CommandText, ArrayList<OracleParam> p_parameters, String dataSourceName,
			Boolean lower) {
		return OpenRS(p_CommandText, p_parameters, lower);
	}

	public Response OpenRS(String p_CommandText, ArrayList<OracleParam> p_parameters, DataSource dataSource) {
		return OpenRS(p_CommandText, p_parameters);
	}

	public Response OpenRS(String p_CommandText, ArrayList<OracleParam> p_parameters) {

		return OpenRS(p_CommandText, p_parameters, true);

	}

	public Response OpenRS(String p_CommandText, ArrayList<OracleParam> p_parameters, Boolean lower) {

		Response response = new Response();
		List<Object> result = new ArrayList<Object>();
		try {
			_openRS(p_CommandText, p_parameters, response, result, lower);
		} catch (SQLException e) {

			response.error = "1";
			response.message = e.getMessage();
			String Stringmessage = "";
			Stringmessage = Stringmessage + p_CommandText.toUpperCase() + "\n";
			Stringmessage += "Input: \n";
			for (OracleParam value : p_parameters) {
				Stringmessage = Stringmessage + (value.getParameterName() + ": " + value.getOracleValue() + "\n");
			}
			Stringmessage = Stringmessage + "Output: \n" + e.getMessage();
			vn.vnpt.common.callHttp.optionsCall(Stringmessage);

		} catch (Exception e) {

			response.error = "1";
			response.message = e.getMessage();
			String Stringmessage = "";
			Stringmessage = Stringmessage + p_CommandText.toUpperCase() + "\n";
			for (OracleParam value : p_parameters) {
				Stringmessage = Stringmessage + (value.getParameterName() + ": " + value.getOracleValue() + "\n");
			}
			Stringmessage = Stringmessage + "Output: \n" + e.getMessage();

			vn.vnpt.common.callHttp.optionsCall(Stringmessage);

		}

		return response;

	}

	private void _openRS(String p_CommandText, ArrayList<OracleParam> p_parameters, Response response,
			List<Object> result, Boolean lower) throws SQLException {
		StringBuilder ColumIndex = new StringBuilder();

		for (int i = 0; i < p_parameters.size(); i++) {
			ColumIndex.append("?,");
		}
		if (ColumIndex.length() > 0)
			ColumIndex = new StringBuilder(ColumIndex.substring(0, ColumIndex.length() - 1));

		try (Handle sql = Jdbi.create(getDataSource()).open()) {
			Call cStmt = sql.createCall("{call " + p_CommandText + "(" + ColumIndex + ") }");
			int i = 0;
			int indexOutput = 0;
			for (OracleParam value : p_parameters) {
				if (value.getDirect() == OracleDirect.INPUT) {
					cStmt.bind(i, value.getOracleValue());
				} else {
					indexOutput = i;
					cStmt.registerOutParameter(indexOutput, OracleTypes.CURSOR);
				}
				i++;
			}
			int Output = indexOutput;
			cStmt.invoke(outParameters -> {
				ResultSet rs = (ResultSet) outParameters.getObject(Output);
				try {
					if (rs != null) {
						ResultSetMetaData mtrs = rs.getMetaData();

						while (rs.next()) {
							int cols = rs.getMetaData().getColumnCount();

							Map<String, Object> map = new HashMap<>();
							for (int j = 0; j < cols; j++) {
								if (acceptnull) {
									if (mtrs.getColumnTypeName(j + 1) == "DATE") {
										if (rs.getObject(j + 1) != null) {
											map.put(lower == true ? mtrs.getColumnName(j + 1).toLowerCase()
													: mtrs.getColumnName(j + 1), rs.getObject(j + 1).toString());
										}
									} else {
										map.put(lower == true ? mtrs.getColumnName(j + 1).toLowerCase()
												: mtrs.getColumnName(j + 1), rs.getObject(j + 1));
									}
								} else {

									if (rs.getObject(j + 1) != null)
										if (mtrs.getColumnTypeName(j + 1) == "DATE") {
											map.put(lower == true ? mtrs.getColumnName(j + 1).toLowerCase()
													: mtrs.getColumnName(j + 1), rs.getObject(j + 1).toString());
										} else {
											map.put(lower == true ? mtrs.getColumnName(j + 1).toLowerCase()
													: mtrs.getColumnName(j + 1), rs.getObject(j + 1));
										}
								}
							}

							result.add(map);
						}
						response.data = (ArrayList<Object>) result;
						response.error = null;
						response.message = null;
						rs.close();

					} else {
						response.error = "1";
						response.message = "Không tìm thấy thông tin";
					}
				} catch (SQLException e) {
					response.error = "1";
					response.message = e.getMessage();
				}
			});

		} catch (Exception e) {
			response.error = "1";
			response.message = "Failed to make connection";
		}
	}

	@SuppressWarnings("unused")
	public Response CallFunction(String p_CommandText, ArrayList<OracleParam> p_parameters, DataSource dataSource) {
		StringBuilder ColumIndex = new StringBuilder();
		int indexOutput = 0;
		Response response = new Response();
		List<Object> result = new ArrayList<Object>();
		try (Handle sql = Jdbi.create(getDataSource()).open()) {
			for (int i = 0; i < p_parameters.size(); i++) {
				ColumIndex.append("?,");
			}
			ColumIndex = new StringBuilder(ColumIndex.substring(0, ColumIndex.length() - 1));
			Call cStmt = sql.createCall("{? = call " + p_CommandText + "(" + ColumIndex + ") }");

			cStmt.registerOutParameter(1, OracleTypes.VARCHAR);
			int i = 2;
			for (OracleParam value : p_parameters) {
				if (value.getDirect() == OracleDirect.INPUT) {
					cStmt.bind(i, value.getOracleValue());
				}
				i++;
			}

			OutParameters outParameters = cStmt.invoke();
			ResultSet rs = (ResultSet) outParameters.getObject(indexOutput);
			String stringOut = rs.getString(1);
			Map<String, String> data = new HashMap<>();
			data.put("myVal", stringOut);

			cStmt.close();
			if (stringOut != null) {

				response.data = data;
				response.error = null;
				response.message = null;
			} else {
				response.error = "1";
				response.message = "Không tìm thấy thông tin";
			}

		} catch (SQLException e) {

			response.error = "1";
			response.message = e.getMessage();
			String Stringmessage = "";
			Stringmessage = Stringmessage + p_CommandText.toUpperCase() + "\n";
			Stringmessage += "Input: \n";
			for (OracleParam value : p_parameters) {
				Stringmessage = Stringmessage + (value.getParameterName() + ": " + value.getOracleValue() + "\n");
			}
			Stringmessage = Stringmessage + "Output: \n" + e.getMessage();
			vn.vnpt.common.callHttp.optionsCall(Stringmessage);

		} catch (Exception e) {

			response.error = "1";
			response.message = e.getMessage();
			String Stringmessage = "";
			Stringmessage = Stringmessage + p_CommandText.toUpperCase() + "\n";
			for (OracleParam value : p_parameters) {
				Stringmessage = Stringmessage + (value.getParameterName() + ": " + value.getOracleValue() + "\n");
			}
			Stringmessage = Stringmessage + "Output: \n" + e.getMessage();
			vn.vnpt.common.callHttp.optionsCall(Stringmessage);

		}
		return response;

	}

	public Response execProcedure(String p_procedureName, ArrayList<OracleParamEx<?>> p_parameters) {
		Response response = new Response();
		List<Object> result = new ArrayList<Object>();
		try {
			try (Handle sql = Jdbi.create(getDataSource()).open()) {
				StringBuilder ColumIndex = new StringBuilder();
				int iCursor = 0;
				for (int i = 0; i < p_parameters.size(); i++) {
					ColumIndex.append("?,");
				}
				ColumIndex = new StringBuilder(ColumIndex.substring(0, ColumIndex.length() - 1));
				Call call = sql.createCall("{call " + p_procedureName + "(" + ColumIndex + ")}");
				int i = 0;
				for (OracleParamEx<?> value : p_parameters) {
					if (value.getDirect() == vn.vnpt.database.OracleParamEx.OracleDirect.INPUT) {
						if (value.getOracleDbType() == vn.vnpt.database.OracleParamEx.OracleType.VARCHAR2) {
							call.bind(i, value.getOracleValue());
						} else if (value.getOracleDbType() == vn.vnpt.database.OracleParamEx.OracleType.NUMBER) {
							call.bind(i, value.getOracleValue());
						} else if (value.getOracleDbType() == vn.vnpt.database.OracleParamEx.OracleType.DATE) {
							call.bind(i, value.getOracleValue());
						} else if (value.getOracleDbType() == vn.vnpt.database.OracleParamEx.OracleType.ARRAY) {
							call.bindArray(i, value.getOracleValue());
						}
					} else {
						call.registerOutParameter(i, OracleTypes.CURSOR);
						iCursor = i;
					}
					i++;
				}
				final int nCursor = iCursor;
				call.invoke(outParameters -> {
					ResultSet rs = (ResultSet) outParameters.getObject(nCursor);
					try {
						if (rs != null) {
							ResultSetMetaData metaData = rs.getMetaData();
							final int cols = rs.getMetaData().getColumnCount();
							while (rs.next()) {
								Map<String, Object> map = new HashMap<>();
								for (int j = 0; j < cols; j++) {

									if (metaData.getColumnTypeName(j + 1) == "DATE") {
										if (rs.getObject(j + 1) != null) {
											map.put(metaData.getColumnName(j + 1).toLowerCase(),
													rs.getObject(j + 1).toString());
										}
									} else {
										map.put(metaData.getColumnName(j + 1).toLowerCase(), rs.getObject(j + 1));
									}
								}
								result.add(map);
							}
							rs.close();
							response.data = (ArrayList<Object>) result;
							response.error = null;
							response.message = null;
						} else {
							response.data = null;
							response.error = null;
							response.message = null;
						}
					} catch (SQLException e) {
						response.error = "1";
						response.message = e.getMessage();
						String Stringmessage = "";
						Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";
						for (OracleParamEx<?> value : p_parameters) {
							Stringmessage = Stringmessage
									+ (value.getParameterName() + ": " + value.getOracleValue() + "\n");
						}
						Stringmessage = Stringmessage + "Output: \n" + e.getMessage();
						vn.vnpt.common.callHttp.optionsCall(Stringmessage);
					}
				});
			} catch (JdbiException e) {
				response.error = "1";
				response.message = e.getMessage();
				String Stringmessage = "";
				Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";
				for (OracleParamEx<?> value : p_parameters) {
					Stringmessage = Stringmessage + (value.getParameterName() + ": " + value.getOracleValue() + "\n");
				}
				Stringmessage = Stringmessage + "Output: \n" + e.getMessage();

				vn.vnpt.common.callHttp.optionsCall(Stringmessage);
			}
		} catch (Exception e) {
			response.error = "1";
			response.message = e.getMessage();
			String Stringmessage = "";
			Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";
			for (OracleParamEx<?> value : p_parameters) {
				Stringmessage = Stringmessage + (value.getParameterName() + ": " + value.getOracleValue() + "\n");
			}
			Stringmessage = Stringmessage + "Output: \n" + e.getMessage();

			vn.vnpt.common.callHttp.optionsCall(Stringmessage);
		}
		return response;
	}

	public Response execFunction(String p_procedureName, ArrayList<OracleParamEx<?>> p_parameters) {
		Response response = new Response();
		List<Object> result = new ArrayList<Object>();
		try {
			try (Handle sql = Jdbi.create(getDataSource()).open()) {
				StringBuilder ColumIndex = new StringBuilder();
				int iCursor = 0;
				for (int i = 0; i < p_parameters.size() - 1; i++) {
					ColumIndex.append("?,");
				}
				ColumIndex = new StringBuilder(ColumIndex.substring(0, ColumIndex.length() - 1));
				Call call = sql.createCall("{ ? = call " + p_procedureName + "(" + ColumIndex + ")}");
				int i = 0;
				for (OracleParamEx<?> value : p_parameters) {
					if (value.getDirect() == vn.vnpt.database.OracleParamEx.OracleDirect.INPUT) {
						if (value.getOracleDbType() == vn.vnpt.database.OracleParamEx.OracleType.VARCHAR2) {
							call.bind(i, value.getOracleValue());
						} else if (value.getOracleDbType() == vn.vnpt.database.OracleParamEx.OracleType.NUMBER) {
							call.bind(i, value.getOracleValue());
						} else if (value.getOracleDbType() == vn.vnpt.database.OracleParamEx.OracleType.DATE) {
							call.bind(i, value.getOracleValue());
						}
					} else {
						call.registerOutParameter(i, OracleTypes.CURSOR);
						iCursor = i;
					}
					i++;
				}
				int nCursor = iCursor;
				call.invoke(outParameters -> {
					ResultSet rs = (ResultSet) outParameters.getObject(nCursor);
					try {
						if (rs != null) {
							ResultSetMetaData metaData = rs.getMetaData();
							final int cols = rs.getMetaData().getColumnCount();
							while (rs.next()) {
								Map<String, Object> map = new HashMap<>();
								for (int j = 0; j < cols; j++) {

									if (metaData.getColumnTypeName(j + 1) == "DATE") {
										if (rs.getObject(j + 1) != null) {
											map.put(metaData.getColumnName(j + 1).toLowerCase(),
													rs.getObject(j + 1).toString());
										}
									} else {
										map.put(metaData.getColumnName(j + 1).toLowerCase(), rs.getObject(j + 1));
									}
								}
								result.add(map);
							}
							rs.close();
							response.data = (ArrayList<Object>) result;
							response.error = null;
							response.message = null;
						} else {
							response.data = null;
							response.error = null;
							response.message = null;
						}
					} catch (SQLException e) {
						response.error = "1";
						response.message = e.getMessage();
						String Stringmessage = "";
						Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";
						for (OracleParamEx<?> value : p_parameters) {
							Stringmessage = Stringmessage
									+ (value.getParameterName() + ": " + value.getOracleValue() + "\n");
						}
						Stringmessage = Stringmessage + "Output: \n" + e.getMessage();

						vn.vnpt.common.callHttp.optionsCall(Stringmessage);
					}
				});
			} catch (JdbiException e) {
				response.error = "1";
				response.message = e.getMessage();
				String Stringmessage = "";
				Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";
				for (OracleParamEx<?> value : p_parameters) {
					Stringmessage = Stringmessage + (value.getParameterName() + ": " + value.getOracleValue() + "\n");
				}
				Stringmessage = Stringmessage + "Output: \n" + e.getMessage();
				vn.vnpt.common.callHttp.optionsCall(Stringmessage);
			}
		} catch (Exception e) {
			response.error = "1";
			response.message = e.getMessage();
			String Stringmessage = "";
			Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";
			for (OracleParamEx<?> value : p_parameters) {
				Stringmessage = Stringmessage + (value.getParameterName() + ": " + value.getOracleValue() + "\n");
			}
			Stringmessage = Stringmessage + "Output: \n" + e.getMessage();
			vn.vnpt.common.callHttp.optionsCall(Stringmessage);
		}
		return response;
	}

	public Response execProcedure(String p_procedureName, Map<String, Object> p_parameters) {
		Response response = new Response();
		List<Object> result = new ArrayList<Object>();
		try {
			try (Handle sql = Jdbi.create(getDataSource()).open()) {
				StringBuilder ColumIndex = new StringBuilder();
				for (Map.Entry<String, Object> parameter : p_parameters.entrySet()) {
					ColumIndex.append(":" + parameter.getKey() + ",");
				}
				ColumIndex = new StringBuilder(ColumIndex.substring(0, ColumIndex.length() - 1));
				String t_procedureName = "{call " + p_procedureName + "(" + ColumIndex + ")}";
				t_procedureName = t_procedureName.substring(0, t_procedureName.length() - 2) + ",:cursor)}";
				Call call = sql.createCall(t_procedureName);
				if (null != p_parameters) {
					for (Map.Entry<String, Object> parameter : p_parameters.entrySet()) {
						call.bind(parameter.getKey(), parameter.getValue());
					}
				}
				call.registerOutParameter("cursor", OracleTypes.CURSOR);
				call.invoke(outParameters -> {
					ResultSet rs = (ResultSet) outParameters.getObject("cursor");
					try {
						if (rs != null) {
							ResultSetMetaData metaData = rs.getMetaData();
							final int cols = rs.getMetaData().getColumnCount();
							while (rs.next()) {
								Map<String, Object> map = new HashMap<>();
								for (int j = 0; j < cols; j++) {

									if (metaData.getColumnTypeName(j + 1) == "DATE") {
										if (rs.getObject(j + 1) != null) {
											map.put(metaData.getColumnName(j + 1).toLowerCase(),
													rs.getObject(j + 1).toString());
										}
									} else {
										map.put(metaData.getColumnName(j + 1).toLowerCase(), rs.getObject(j + 1));
									}
								}
								result.add(map);
							}
							rs.close();
							response.data = (ArrayList<Object>) result;
							response.error = null;
							response.message = null;
						} else {
							response.data = null;
							response.error = null;
							response.message = null;
						}
					} catch (SQLException e) {
						response.error = "1";
						response.message = e.getMessage();
						String Stringmessage = "";
						Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";

						for (Map.Entry<String, Object> entry : p_parameters.entrySet()) {
							Stringmessage = Stringmessage + (entry.getKey() + ": " + entry.getValue() + "\n");
						}
						Stringmessage = Stringmessage + "Output: \n" + e.getMessage();
						vn.vnpt.common.callHttp.optionsCall(Stringmessage);
					}
				});
			} catch (JdbiException e) {
				response.error = "1";
				String msg = e.getMessage();				
				String[] bits = msg.split("\n");
				if (bits.length > 0 && bits[0].contains(":")) {
					
				}
				response.message = msg;

				String Stringmessage = "";
				Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";

				for (Map.Entry<String, Object> entry : p_parameters.entrySet()) {
					Stringmessage = Stringmessage + (entry.getKey() + ": " + entry.getValue() + "\n");
				}

				Stringmessage = Stringmessage + "Output: \n" + e.getMessage();

				vn.vnpt.common.callHttp.optionsCall(Stringmessage);
			}
		} catch (Exception e) {
			response.error = "1";
			response.message = e.getMessage();
			String Stringmessage = "";
			Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";

			for (Map.Entry<String, Object> entry : p_parameters.entrySet()) {
				Stringmessage = Stringmessage + (entry.getKey() + ": " + entry.getValue() + "\n");
			}
			Stringmessage = Stringmessage + "Output: \n" + e.getMessage();
			vn.vnpt.common.callHttp.optionsCall(Stringmessage);
		}
		return response;
	}

	public Response execProcedure(String p_procedureName, String[] p_parameters) {
		Response response = new Response();
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		for (int i = 0; i < p_parameters.length; i++) {
			parameters.put(String.valueOf(i), p_parameters[i].toString());
		}
		response = this.execProcedure(p_procedureName, parameters);
		return response;
	}

	public Response execFunction(String p_procedureName, String[] p_parameters) {
		Response response = new Response();
		Map<String, Object> parameters = new HashMap<>();
		for (int i = 0; i < p_parameters.length; i++) {
			parameters.put(String.valueOf(i), p_parameters[i].toString());
		}
		response = this.execFunction(p_procedureName, parameters);
		return response;
	}

	public Response execFunction(String p_procedureName, Map<String, Object> p_parameters) {
		Response response = new Response();
		List<Object> result = new ArrayList<Object>();
		try {
			try (Handle sql = Jdbi.create(getDataSource()).open()) {
				StringBuilder ColumIndex = new StringBuilder();
				for (Map.Entry<String, Object> parameter : p_parameters.entrySet()) {
					ColumIndex.append(":" + parameter.getKey() + ",");
				}
				ColumIndex = new StringBuilder(ColumIndex.substring(0, ColumIndex.length() - 1));
				String t_procedureName = "{:cursor = call " + p_procedureName + "(" + ColumIndex + ")}";
				Call call = sql.createCall(t_procedureName);
				if (null != p_parameters) {
					for (Map.Entry<String, Object> parameter : p_parameters.entrySet()) {
						call.bind(parameter.getKey(), parameter.getValue());
					}
				}
				call.registerOutParameter("cursor", OracleTypes.CURSOR);
				call.invoke(outParameters -> {
					ResultSet rs = (ResultSet) outParameters.getObject("cursor");
					try {
						if (rs != null) {
							ResultSetMetaData metaData = rs.getMetaData();
							final int cols = rs.getMetaData().getColumnCount();
							while (rs.next()) {
								Map<String, Object> map = new HashMap<>();
								for (int j = 0; j < cols; j++) {

									if (metaData.getColumnTypeName(j + 1) == "DATE") {
										if (rs.getObject(j + 1) != null) {
											map.put(metaData.getColumnName(j + 1).toLowerCase(),
													rs.getObject(j + 1).toString());
										}
									} else {
										map.put(metaData.getColumnName(j + 1).toLowerCase(), rs.getObject(j + 1));
									}
								}
								result.add(map);
							}
							rs.close();
							response.data = (ArrayList<Object>) result;
							response.error = null;
							response.message = null;
						} else {
							response.data = null;
							response.error = null;
							response.message = null;
						}
					} catch (SQLException e) {
						response.error = "1";
						response.message = e.getMessage();
						String Stringmessage = "";
						Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";

						for (Map.Entry<String, Object> entry : p_parameters.entrySet()) {
							Stringmessage = Stringmessage + (entry.getKey() + ": " + entry.getValue() + "\n");
						}
						Stringmessage = Stringmessage + "Output: \n" + e.getMessage();
						vn.vnpt.common.callHttp.optionsCall(Stringmessage);
					}
				});
			} catch (JdbiException e) {
				response.error = "1";
				response.message = e.getMessage();
				String Stringmessage = "";
				Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";

				for (Map.Entry<String, Object> entry : p_parameters.entrySet()) {
					Stringmessage = Stringmessage + (entry.getKey() + ": " + entry.getValue() + "\n");
				}

				Stringmessage = Stringmessage + "Output: \n" + e.getMessage();

				vn.vnpt.common.callHttp.optionsCall(Stringmessage);
			}
		} catch (Exception e) {
			response.error = "1";
			response.message = e.getMessage();
			String Stringmessage = "";
			Stringmessage = Stringmessage + p_procedureName.toUpperCase() + "\n";

			for (Map.Entry<String, Object> entry : p_parameters.entrySet()) {
				Stringmessage = Stringmessage + (entry.getKey() + ": " + entry.getValue() + "\n");
			}
			Stringmessage = Stringmessage + "Output: \n" + e.getMessage();
			vn.vnpt.common.callHttp.optionsCall(Stringmessage);
		}
		return response;
	}

	public List<Map<String, Object>> resultSetToArrayList(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		List<Map<String, Object>> list = new ArrayList<>();
		while (rs.next()) {
			Map<String, Object> row = new HashMap<>(columns);
			for (int i = 1; i <= columns; ++i) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(row);
		}

		return list;
	}
}
