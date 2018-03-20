package bao_excel.org.bao.excel.poi.bean;

public class BeanSheet2{
		private String appType;
		private String account;
		private String recordTime;
		private String caseName;
		private String userName;
		public BeanSheet2(String appType, String account, String recordTime) {
			super();
			this.appType = appType;
			this.account = account;
			this.recordTime = recordTime;
		}
		public String getAppType() {
			return appType;
		}
		public void setAppType(String appType) {
			this.appType = appType;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getRecordTime() {
			return recordTime;
		}
		public void setRecordTime(String recordTime) {
			this.recordTime = recordTime;
		}
		public String getCaseName() {
			return caseName;
		}
		public void setCaseName(String caseName) {
			this.caseName = caseName;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public BeanSheet2(String appType, String account, String recordTime,
				String caseName, String userName) {
			super();
			this.appType = appType;
			this.account = account;
			this.recordTime = recordTime;
			this.caseName = caseName;
			this.userName = userName;
		}
		
		
		
		
	}