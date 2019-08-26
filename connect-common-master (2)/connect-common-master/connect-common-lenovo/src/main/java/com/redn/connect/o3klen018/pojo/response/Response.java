package com.redn.connect.o3klen018.pojo.response;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public String status;
	public String status_code;
	public String status_desc;
	public String srv_number;
	public String srv_type;
	public String req_del_date;
	public String req_del_date_time_window;
	public String service_time_zone_offset;
	public String priority;
	public String srv_del_level;
	public String ship_to_city;
	public String ship_to_region;
	public String ship_to_country;
	public String ship_to_postal_code;
	public String premier_customer;
	public String zvenid;
	public String zpudo;
	public String zzmt;
	public String warranty_info;
	public String hdd_retention;
	public String customer_id;
	public String zph1;
	public String zph2;
	public String header_ref1;
	public String header_ref2;
	public String header_ref3;
	public String header_ref4;
	public String header_ref5;
	public String system_id;
	
	
	public List<Item> item;

	public String getSrv_number() {
		return srv_number;
	}

	public void setSrv_number(String srv_number) {
		this.srv_number = srv_number;
	}

	public String getSrv_type() {
		return srv_type;
	}

	public void setSrv_type(String srv_type) {
		this.srv_type = srv_type;
	}

	public String getReq_del_date() {
		return req_del_date;
	}

	public void setReq_del_date(String req_del_date) {
		this.req_del_date = req_del_date;
	}

	public String getReq_del_date_time_window() {
		return req_del_date_time_window;
	}

	public void setReq_del_date_time_window(String req_del_date_time_window) {
		this.req_del_date_time_window = req_del_date_time_window;
	}

	public String getService_time_zone_offset() {
		return service_time_zone_offset;
	}

	public void setService_time_zone_offset(String service_time_zone_offset) {
		this.service_time_zone_offset = service_time_zone_offset;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSrv_del_level() {
		return srv_del_level;
	}

	public void setSrv_del_level(String srv_del_level) {
		this.srv_del_level = srv_del_level;
	}

	public String getShip_to_city() {
		return ship_to_city;
	}

	public void setShip_to_city(String ship_to_city) {
		this.ship_to_city = ship_to_city;
	}

	public String getShip_to_region() {
		return ship_to_region;
	}

	public void setShip_to_region(String ship_to_region) {
		this.ship_to_region = ship_to_region;
	}

	public String getShip_to_country() {
		return ship_to_country;
	}

	public void setShip_to_country(String ship_to_country) {
		this.ship_to_country = ship_to_country;
	}

	public String getShip_to_postal_code() {
		return ship_to_postal_code;
	}

	public void setShip_to_postal_code(String ship_to_postal_code) {
		this.ship_to_postal_code = ship_to_postal_code;
	}

	public String getPremier_customer() {
		return premier_customer;
	}

	public void setPremier_customer(String premier_customer) {
		this.premier_customer = premier_customer;
	}

	public String getZvenid() {
		return zvenid;
	}

	public void setZvenid(String zvenid) {
		this.zvenid = zvenid;
	}

	public String getZpudo() {
		return zpudo;
	}

	public void setZpudo(String zpudo) {
		this.zpudo = zpudo;
	}

	public String getZzmt() {
		return zzmt;
	}

	public void setZzmt(String zzmt) {
		this.zzmt = zzmt;
	}

	public String getWarranty_info() {
		return warranty_info;
	}

	public void setWarranty_info(String warranty_info) {
		this.warranty_info = warranty_info;
	}

	public String getHdd_retention() {
		return hdd_retention;
	}

	public void setHdd_retention(String hdd_retention) {
		this.hdd_retention = hdd_retention;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getZph1() {
		return zph1;
	}

	public void setZph1(String zph1) {
		this.zph1 = zph1;
	}

	public String getZph2() {
		return zph2;
	}

	public void setZph2(String zph2) {
		this.zph2 = zph2;
	}

	public String getHeader_ref1() {
		return header_ref1;
	}

	public void setHeader_ref1(String header_ref1) {
		this.header_ref1 = header_ref1;
	}

	public String getHeader_ref2() {
		return header_ref2;
	}

	public void setHeader_ref2(String header_ref2) {
		this.header_ref2 = header_ref2;
	}

	public String getHeader_ref3() {
		return header_ref3;
	}

	public void setHeader_ref3(String header_ref3) {
		this.header_ref3 = header_ref3;
	}

	public String getHeader_ref4() {
		return header_ref4;
	}

	public void setHeader_ref4(String header_ref4) {
		this.header_ref4 = header_ref4;
	}

	public String getHeader_ref5() {
		return header_ref5;
	}

	public void setHeader_ref5(String header_ref5) {
		this.header_ref5 = header_ref5;
	}

	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getStatus_desc() {
		return status_desc;
	}

	public void setStatus_desc(String status_desc) {
		this.status_desc = status_desc;
	}
}

