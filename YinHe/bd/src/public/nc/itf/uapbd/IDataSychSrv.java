/**
 * 
 */
package nc.itf.uapbd;

import nc.vo.pub.BusinessException;

/**
 * ����ͬ������
 * @author yuxx3
 * @created at 2016��4��14��,����4:34:26
 *
 */
public interface IDataSychSrv {
	/**
	 * ͬ������
	 * @create by yuxx3 at 2016��4��14��,����4:35:52
	 *
	 * @throws BusinessException
	 */
	public void mateSych()throws BusinessException;
	/**
	 * ͬ��BOM
	 * @create by yuxx3 at 2016��4��14��,����4:36:15
	 *
	 * @throws BusinessException
	 */
	public void bomSych()throws BusinessException;

}
