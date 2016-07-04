package nc.impl.report;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;

import nc.bs.dao.BaseDAO;
import nc.itf.report.IFreeReport;
import nc.pub.smart.context.SmartContext;
import nc.vo.pub.BusinessException;
import nc.vo.pub.report.BaseQueryCondition.BaseQueryCondition;
import nc.vo.pubapp.AppContext;

import com.ufida.report.anareport.FreeReportContextKey;

public class FreeReportImpl implements IFreeReport {
		
	/**
	 * ��Ʊ��Ӧ������ϸ����
	 * @param  SmartContext
	 * @return String
	 * @throws BusinessException
	 * @throws Exception
	 */
	@Override
	public String iInvoiceToAllocationDtl(Object pk_org,Object pk_customer,Object pk_material,
			Object stodoc,Object startdate,Object enddate) throws Exception {
		Connection conn = DBUtil.getConn();
		//��ñ�������ѯ����
		//BaseQueryCondition query_cond=(BaseQueryCondition)context.getAttribute(FreeReportContextKey.KEY_IQUERYCONDITION);
		//HashMap<String,Object> cond_map=(HashMap<String,Object>)query_cond.getUserObject();
		String query = "call pkg_report_pro.invoice_correspond_allocation(?,?,?,?,?,?,?)"; // �˴�����ǰ�潨���Ĵ洢���̣�
		CallableStatement cstmt = conn.prepareCall(query);
		cstmt.setObject(1, pk_customer);//�ͻ�
		cstmt.setObject(2, pk_org);//��֯
		cstmt.setObject(3, startdate);//��Ʊ��ʼ����
		cstmt.setObject(4, enddate);//��Ʊ��������
		cstmt.setObject(5, pk_material);//����
		cstmt.setObject(6, stodoc);//�ֿ�
		cstmt.setObject(7, AppContext.getInstance().getPkUser());//��ǰ��¼��
		cstmt.execute();
		//���ز�ѯsql
		return "select * from v_invoice_to_allocation";
	}

	/**
	 * �տ���ϸ��Ӧ��Ʊ��������ϸ
	 * @param  SmartContext
	 * @return String
	 * @throws BusinessException
	 * @throws Exception
	 */
	@Override
	public String iGatherToInvoiceAndAllocationDtl(Object startday,Object endday) throws Exception {
		Connection conn = DBUtil.getConn();
		//��ñ�������ѯ����
		//BaseQueryCondition query_cond=(BaseQueryCondition)context.getAttribute(FreeReportContextKey.KEY_IQUERYCONDITION);
		//HashMap<String,Object> cond_map=(HashMap<String,Object>)query_cond.getUserObject();
		String query = "call pkg_report_pro.gather_correspond_invoice(?,?,?)"; // �˴�����ǰ�潨���Ĵ洢���̣�
		CallableStatement cstmt = conn.prepareCall(query);
		cstmt.setObject(1, startday);//��Ʊ��ʼ����
		cstmt.setObject(2, endday);//��Ʊ��������
		cstmt.setObject(3, AppContext.getInstance().getPkUser());//��ǰ��¼��
		cstmt.execute();
		//���ز�ѯsql
		return "select * from v_gather_to_invoiceallocation";
	}
	
	/**
	 * ��Ʊ�ӳٷ�����ϸ�������ܣ�
	 * @param  SmartContext
	 * @return String
	 * @throws BusinessException
	 * @throws Exception
	 */
	@Override
	public String iDelayPenalty(SmartContext context) throws Exception {
		Connection conn = DBUtil.getConn();
		//��ñ�������ѯ����
		//BaseQueryCondition query_cond=(BaseQueryCondition)context.getAttribute(FreeReportContextKey.KEY_IQUERYCONDITION);
		//HashMap<String,Object> cond_map=(HashMap<String,Object>)query_cond.getUserObject();
		String query = "call pkg_report_pro.invoice_delay_penalty(?)"; // �˴�����ǰ�潨���Ĵ洢���̣�
		CallableStatement cstmt = conn.prepareCall(query);
		cstmt.setObject(1, AppContext.getInstance().getPkUser());//��ǰ��¼��
		cstmt.execute();
		//���ز�ѯsql
		return "select * from report_invoice_delay_penalty";
	}

	/**
	 * ����δ��Ʊ�����������
	 * @param  SmartContext
	 * @return String
	 * @throws BusinessException
	 * @throws Exception
	 */
	@Override
	public String iAllocationUnbilled(SmartContext context) throws Exception {
		Connection conn = DBUtil.getConn();
		//��ñ�������ѯ����
		//BaseQueryCondition query_cond=(BaseQueryCondition)context.getAttribute(FreeReportContextKey.KEY_IQUERYCONDITION);
		//HashMap<String,Object> cond_map=(HashMap<String,Object>)query_cond.getUserObject();
		String query = "call pkg_report_pro.unbilledmaterialageanalysis(?)"; // �˴�����ǰ�潨���Ĵ洢���̣�
		CallableStatement cstmt = conn.prepareCall(query);
		cstmt.setObject(1, AppContext.getInstance().getPkUser());//��ǰ��¼��
		cstmt.execute();
		//���ز�ѯsql
		return "select * from v_unbilledmatageanalysis";
	}
	
	/**
	 * �����ƻ�������һ����
	 * @param  SmartContext
	 * @return String
	 * @throws BusinessException
	 * @throws Exception
	 */
	@Override
	public String iQualityTracking(SmartContext context) throws Exception {
		Connection conn = DBUtil.getConn();
		//��ñ�������ѯ����
		//BaseQueryCondition query_cond=(BaseQueryCondition)context.getAttribute(FreeReportContextKey.KEY_IQUERYCONDITION);
		//HashMap<String,Object> cond_map=(HashMap<String,Object>)query_cond.getUserObject();
		String query = "call pkg_report_pro.quality_plan_tracking(?)"; // �˴�����ǰ�潨���Ĵ洢���̣�
		CallableStatement cstmt = conn.prepareCall(query);
		cstmt.setObject(1, AppContext.getInstance().getPkUser());//��ǰ��¼��
		cstmt.execute();
		//���ز�ѯsql
		return "select * from v_quality_plan_tracking";
	}
	
	/**
	 * ���ۻ�����ɶ���ϸ�������ܣ�
	 * @param  SmartContext
	 * @return String
	 * @throws BusinessException
	 * @throws Exception
	 */
	@Override
	public String iSalesRecoveryommission(Object startday,Object endday) throws Exception {
		Connection conn = DBUtil.getConn();
		//��ñ�������ѯ����
		//BaseQueryCondition query_cond=(BaseQueryCondition)context.getAttribute(FreeReportContextKey.KEY_IQUERYCONDITION);
		//HashMap<String,Object> cond_map=(HashMap<String,Object>)query_cond.getUserObject();
		String query = "call pkg_report_pro.gather_correspond_invoice(?,?,?)"; // �˴�����ǰ�潨���Ĵ洢���̣�
		CallableStatement cstmt = conn.prepareCall(query);
		cstmt.setObject(1, startday);//��Ʊ��ʼ����
		cstmt.setObject(2, endday);//��Ʊ��������
		cstmt.setObject(3, AppContext.getInstance().getPkUser());//��ǰ��¼��
		cstmt.execute();
		//���ز�ѯsql
		return "select * from v_gather_to_invoiceallocation";
	}
	
	/**
	 * Ӧ�տ�Ԥ����ϸ�������ܣ�
	 * @param  SmartContext
	 * @return String
	 * @throws BusinessException
	 * @throws Exception
	 */
	@Override
	public String iReceivableEarlyWarning(SmartContext context) throws Exception {
		Connection conn = DBUtil.getConn();
		//��ñ�������ѯ����
		//BaseQueryCondition query_cond=(BaseQueryCondition)context.getAttribute(FreeReportContextKey.KEY_IQUERYCONDITION);
		//HashMap<String,Object> cond_map=(HashMap<String,Object>)query_cond.getUserObject();
		String query = "call pkg_report_pro.receivable_earlywaring_dtl(?)"; // �˴�����ǰ�潨���Ĵ洢���̣�
		CallableStatement cstmt = conn.prepareCall(query);
		cstmt.setObject(1, AppContext.getInstance().getPkUser());//��ǰ��¼��
		cstmt.execute();
		//���ز�ѯsql
		return "select * from receivable_earlywaring_dtl";
	}
	
	/**
	 * Ӧ�տ��������
	 * @param  SmartContext
	 * @return String
	 * @throws BusinessException
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Override
	public String iReceivablesAgingAnalysis() throws Exception {
		Connection conn = DBUtil.getConn();
		//��ñ�������ѯ����
		//BaseQueryCondition query_cond=(BaseQueryCondition)context.getAttribute(FreeReportContextKey.KEY_IQUERYCONDITION);
		//HashMap<String,Object> cond_map=(HashMap<String,Object>)query_cond.getUserObject();
		String query = "{call pkg_report_pro.yingshouzlanalysis(?)}"; // �˴�����ǰ�潨���Ĵ洢���̣�
		CallableStatement cstmt = conn.prepareCall(query);
		cstmt.setObject(1, AppContext.getInstance().getPkUser());//��ǰ��¼��
		cstmt.execute();
		//���ز�ѯsql
		return "select * from report_yingshou_zl_analyse";
	}
}
