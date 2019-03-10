package com.apeny.servletjsp.domain.sharding;

/**
 * Created by monis on 2019/1/27.
 */
public class FeeVoucher {
    private String systemNo;

    private String systemTime;

    private String sourceTxNo;

    private String institutionID;

    private String txType;

    private String sourceTxSN;

    private String sourceTxTime;

    private int status;

    private String accountTime;

    public String getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getSourceTxNo() {
        return sourceTxNo;
    }

    public void setSourceTxNo(String sourceTxNo) {
        this.sourceTxNo = sourceTxNo;
    }

    public String getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(String institutionID) {
        this.institutionID = institutionID;
    }

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public String getSourceTxSN() {
        return sourceTxSN;
    }

    public void setSourceTxSN(String sourceTxSN) {
        this.sourceTxSN = sourceTxSN;
    }

    public String getSourceTxTime() {
        return sourceTxTime;
    }

    public void setSourceTxTime(String sourceTxTime) {
        this.sourceTxTime = sourceTxTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(String accountTime) {
        this.accountTime = accountTime;
    }
}
