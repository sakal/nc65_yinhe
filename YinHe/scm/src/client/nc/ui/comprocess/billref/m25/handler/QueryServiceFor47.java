/**
 * 
 */
package nc.ui.comprocess.billref.m25.handler;

import nc.bs.framework.common.NCLocator;
import nc.itf.ic.general.query.IICPageQuery;
import nc.itf.ic.m47.ISubcontInMaintain;
import nc.itf.ic.m47.ISubcontInQuery;
import nc.pubitf.ic.m47.m25.ISubcontInQueryFor25;
import nc.pubitf.ic.query.IBillQueryUseScheme;
import nc.pubitf.ic.query.ICommonQuery;
import nc.ui.ic.general.model.ICGenRevisePageService;
import nc.ui.ic.m47.model.SubcontInModelService;
import nc.ui.ic.m47.sourceref.SubcontInBillRefQueryService;
import nc.ui.pu.m25.model.InvoicePageModelService;
import nc.ui.pubapp.uif2app.query2.model.IRefQueryService;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.scmpub.page.PageQueryVO;

/**
 * 
 * �ɹ���Ʊ ���� ί�мӹ���� �Ĳ�ѯ����
 * @author niangzi
 * @created at 2016��4��25��,����4:11:39
 *
 */
public class QueryServiceFor47 implements IRefQueryService {

	@Override
	public Object[] queryByWhereSql(String arg0) throws Exception {
		// TODO �Զ����ɵķ������
		return null;
	}

	@Override
	public Object[] queryByQueryScheme(IQueryScheme queryscheme) throws Exception {
		// TODO �Զ����ɵķ������
//		PageQueryVO rets = null;
		Object[] rets = null;
//		���ܵĽӿڣ�ISubcontInQuery s;
		
//	    InvoicePageModelService service1 = (InvoicePageModelService)NCLocator.getInstance().lookup(InvoicePageModelService.class);
//		rets = service1.query(arg0);
		ISubcontInQueryFor25 s  = NCLocator.getInstance().lookup(ISubcontInQueryFor25.class);
		rets = s.queryBills(queryscheme);
	    return rets;
	}

}
