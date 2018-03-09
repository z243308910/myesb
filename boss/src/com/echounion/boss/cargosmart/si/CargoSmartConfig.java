package com.echounion.boss.cargosmart.si;

import java.util.Map;

/**
 * CargoSmart配置
 * @author 胡礼波
 * 2013-10-14 下午2:32:35
 */
public class CargoSmartConfig {

	private String remoteSi;			//远程服务器上存放SI的目录地址--远程上传
	private String remoteAms;
	private String remoteBr;
	private String remoteIsf;
	
	private String localSi;			//本地服务器上存放SI的目录地址--本地备份
	private String localAck;	   // SI回执文件夹
	private String localAms;
	private String localBr;
	private String localIsf;
	private String localCt;
	private String localBl;
	

	private Map<String,String> monitorLocalPath;	//要监听的文件夹路径--本地文件夹
	
	public String getLocalAck() {
		return localAck;
	}
	public void setLocalAck(String localAck) {
		this.localAck = localAck;
	}
	
	public String getLocalCt() {
		return localCt;
	}
	public void setLocalCt(String localCt) {
		this.localCt = localCt;
	}
	public Map<String, String> getMonitorLocalPath() {
		return monitorLocalPath;
	}
	public String getLocalBl() {
		return localBl;
	}
	public void setLocalBl(String localBl) {
		this.localBl = localBl;
	}
	public void setMonitorLocalPath(Map<String, String> monitorLocalPath) {
		this.monitorLocalPath = monitorLocalPath;
	}
	public String getRemoteSi() {
		return remoteSi;
	}
	public void setRemoteSi(String remoteSi) {
		this.remoteSi = remoteSi;
	}
	public String getRemoteAms() {
		return remoteAms;
	}
	public void setRemoteAms(String remoteAms) {
		this.remoteAms = remoteAms;
	}
	public String getRemoteBr() {
		return remoteBr;
	}
	public void setRemoteBr(String remoteBr) {
		this.remoteBr = remoteBr;
	}
	public String getRemoteIsf() {
		return remoteIsf;
	}
	public void setRemoteIsf(String remoteIsf) {
		this.remoteIsf = remoteIsf;
	}
	public String getLocalSi() {
		return localSi;
	}
	public void setLocalSi(String localSi) {
		this.localSi = localSi;
	}
	public String getLocalAms() {
		return localAms;
	}
	public void setLocalAms(String localAms) {
		this.localAms = localAms;
	}
	public String getLocalBr() {
		return localBr;
	}
	public void setLocalBr(String localBr) {
		this.localBr = localBr;
	}
	public String getLocalIsf() {
		return localIsf;
	}
	public void setLocalIsf(String localIsf) {
		this.localIsf = localIsf;
	}	
}
