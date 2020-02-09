package com.yyjj;


import org.junit.Test;

import com.power.common.util.DateTimeUtil;
import com.power.doc.builder.ApiDocBuilder;
import com.power.doc.builder.HtmlApiDocBuilder;
import com.power.doc.constants.DocGlobalConstants;
import com.power.doc.model.ApiConfig;

public class ApiTest {
	
	String url = "http://127.0.0.1:8080/yyjj";
	/**
	 * 测试生成文档
	 */
	 @Test
	public void testBuilderControllersApiSimple() {
		ApiConfig config = new ApiConfig();
		// 服务地址
		config.setServerUrl(url);
		// 生成到一个文档
		config.setAllInOne(true);
		// 采用严格模式
//		config.isStrict();
		config.setStrict(false);
		// 文档输出路径
		config.setOutPath("./src/main/resources");
		
		//config.setPackageFilters("com.oudot.med.erp.sys.controller.basearchives");
		ApiDocBuilder.builderControllersApi(config);
		// 将生成的文档输出到/Users/dujf/Downloads/md目录下，严格模式下api-doc会检测Controller的接口注释
	}
	
	
    /**
     * 包括设置请求头，缺失注释的字段批量在文档生成期使用定义好的注释
     */
    @Test
    public void testBuilderControllersApi() {
        ApiConfig config = new ApiConfig();
        config.setServerUrl(url);
        //true会严格要求注释，推荐设置true
        config.setStrict(false);
        //true会将文档合并导出到一个markdown
        config.setAllInOne(true);
        //生成html时加密文档名不暴露controller的名称
        config.setMd5EncryptedHtmlName(true);

        //指定文档输出路径
        //@since 1.7 版本开始，选择生成静态html doc文档可使用该路径：DocGlobalConstants.HTML_DOC_OUT_PATH;
        config.setOutPath(DocGlobalConstants.HTML_DOC_OUT_PATH);
        // @since 1.2,如果不配置该选项，则默认匹配全部的controller,
        // 如果需要配置有多个controller可以使用逗号隔开
        config.setPackageFilters("com.oudot.asset.manage.api.controller.project.filelibrary"
        		+ ",com.oudot.asset.manage.api.controller.project"
        		+ ",com.oudot.asset.manage.api.controller.fund"
        		+ ",com.oudot.asset.manage.api.controller.aftervote"
        		+ ",com.oudot.asset.manage.api.controller.report"
        		+ ",com.oudot.asset.manage.api.controller.resource"
        		+ ",com.oudot.asset.manage.api.controller.schedule"
        		+ ",com.oudot.asset.manage.api.controller.personal"
        		+ ",com.oudot.asset.manage.api.controller.system");
        
        //设置请求头，如果没有请求头，可以不用设置
//        config.setRequestHeaders(
//                ApiReqHeader.header().setName("access_token").setType("string").setDesc("Basic auth credentials"),
//                ApiReqHeader.header().setName("user_uuid").setType("string").setDesc("User Uuid key")
//        );

        //设置项目错误码列表，设置自动生成错误列表,
        //如果没需要可以不设置

        //非必须只有当setAllInOne设置为true时文档变更记录才生效，https://gitee.com/sunyurepository/ApplicationPower/issues/IPS4O
//        config.setRevisionLogs(
//                RevisionLog.getLog().setRevisionTime("2018/12/15").setAuthor("chen").setRemarks("测试").setStatus("创建").setVersion("V1.0"),
//                RevisionLog.getLog().setRevisionTime("2018/12/16").setAuthor("chen2").setRemarks("测试2").setStatus("修改").setVersion("V2.0")
//        );


        long start = System.currentTimeMillis();
//        ApiDocBuilder.builderControllersApi(config);

        //@since 1.7+版本开始，smart-doc支持生成带书签的html文档，html文档可选择下面额方式
        HtmlApiDocBuilder.builderControllersApi(config);
        //@since 1.7+版本开始，smart-doc支撑生成AsciiDoc文档，你可以把AsciiDoc转成HTML5的格式。
        //@see https://gitee.com/sunyurepository/api-doc-test
        // AdocDocBuilder.builderControllersApi(config);
                
        long end = System.currentTimeMillis();
        DateTimeUtil.printRunTime(end, start);
    }

}
