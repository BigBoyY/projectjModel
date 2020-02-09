package com.yyjj;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

public class VelocityEngineBuilder {

    private VelocityEngine velocityEngine;


    private VelocityEngineBuilder(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public static VelocityEngineBuilder startWith(VelocityEngine velocityEngine) {
        Objects.requireNonNull("velocityEngine", "velocityEngine 不能为 null");
        return new VelocityEngineBuilder(velocityEngine);
    }

    public static VelocityEngineBuilder startWithDefault() {
        VelocityEngine ve = new VelocityEngine();
// 设置资源路径
        ve.setProperty(org.apache.velocity.runtime.RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
// 初始化
        ve.init();
        return new VelocityEngineBuilder(ve);
    }

    public VelocityTemplate withTemplatePath(Supplier<String> resourcePath) {
        Template template = velocityEngine.getTemplate(resourcePath.get());

        return new VelocityTemplate(velocityEngine, template);
    }


    public VelocityTemplate withTemplate(Supplier<Template> template) {
        Objects.requireNonNull("template", "velocity template 不能为 null");

        return new VelocityTemplate(velocityEngine, template.get());
    }


    public static class VelocityTemplate {
        @SuppressWarnings("unused")
		private VelocityEngine velocityEngine;
        private Template template;

        public VelocityTemplate(VelocityEngine velocityEngine, Template template) {
            this.velocityEngine = velocityEngine;
            this.template = template;
        }

        public VelocityContext withContextBuild() {
            org.apache.velocity.VelocityContext ctx = new org.apache.velocity.VelocityContext();
            return new VelocityContext(template, ctx);
        }

        public VelocityContext withContextBuild(Map<String, String> map) {
            org.apache.velocity.VelocityContext ctx = new org.apache.velocity.VelocityContext(map);
            return new VelocityContext(template, ctx);
        }
    }


    public static class VelocityContext {
        private Template template;
        private org.apache.velocity.VelocityContext context;

        public VelocityContext(Template template, org.apache
            .velocity.VelocityContext context) {
            this.template = template;
            this.context = context;
        }

        public VelocityContext setKV(String k, String v) {
            context.put(k, v);
            return this;
        }

        public VelocityFile toFile(String filePath) {
            StringWriter sw = new StringWriter();
            template.merge(context, sw);
            String result = sw.toString();
            return new VelocityFile(result, filePath);
        }

        public VelocityFile toFile(String filePath, Consumer<Throwable> throwableConsumer) {
            StringWriter sw = new StringWriter();
            template.merge(context, sw);
            String result = sw.toString();
            return new VelocityFile(result, filePath, throwableConsumer);
        }

    }

    public static class VelocityFile {

        private String result;
        private String filePath;
        private Consumer<Throwable> consumer;

        public VelocityFile(String result, String filePath) {
            this.result = result;
            this.filePath = filePath;
        }

        public VelocityFile(String result, String filePath, Consumer<Throwable> consumer) {
            this.result = result;
            this.filePath = filePath;
            this.consumer = consumer;
        }

        public void done() {

            try {

                File file = new File(filePath);
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.append(result);
                fileWriter.flush();
                fileWriter.close();

            } catch (Throwable e) {
                if (consumer != null) {
                    consumer.accept(e);
                }
            }

        }
    }
    @SuppressWarnings("resource")
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
    public static void main(String[] args) {
    	//
    	
    	File file = new File("C:\\Users\\admin\\Documents\\workspace-spring-tool-suite-4-4.4.2.RELEASE\\asset-manage\\asset-manage-db\\src\\main\\java\\com\\oudot\\asset\\manage\\db\\model");
    	String [] models  = scanner("表名，多个英文逗号分割").split(",");
    	fileName(file,models);
    }
    private static void fileName(File file,String [] models) {
    	for (File file2 : file.listFiles()) {
			if(file2.isDirectory()) {
				fileName(file2,models);
			}else {
				VelocityInfo info = new VelocityInfo();
				
				String path = file2.getAbsolutePath();
				path = path.substring(path.indexOf("com"),path.length()-5);				
				path = path.replace("\\",".");
				info.setModelPacage(path);
				String model = path.substring(path.lastIndexOf(".")+1);	
				for (String string : models) {
					if(string.toLowerCase().equals(model.toLowerCase())) {
						info.setModel(model);								
						path = path.substring(0,path.lastIndexOf("."));				
						info.setModule(path = path.substring(path.lastIndexOf(".")+1,path.length())); 
						info.setPackageName("com.oudot.asset.manage.service.model.service");
						init(info);
					}
				}				
			}
		} 
    }
    private static void init(VelocityInfo info) {
    	String path = "C:\\Users\\admin\\Documents\\workspace-spring-tool-suite-4-4.4.2.RELEASE\\asset-manage\\asset-manage-service\\src\\main\\java\\com\\oudot\\asset\\manage\\service";
    	VelocityEngineBuilder.startWithDefault()
	    .withTemplatePath(() -> "vm/service.java.vm")
	    .withContextBuild()
	    .setKV("package", info.getPackageName())
	    .setKV("module", info.getModule())
	    .setKV("model", info.getModel())
	    .setKV("modelPackage", info.getModelPacage())
	    .toFile(path+"\\model\\service\\"+info.getModel()+"Service.java")
	    .done();
    	
    	VelocityEngineBuilder.startWithDefault()
	    .withTemplatePath(() -> "vm/controller.java.vm")
	    .withContextBuild()
	    .setKV("package", info.getPackageName())
	    .setKV("module", info.getModule())
	    .setKV("model", info.getModel())
	    .setKV("lowerModel", info.getModel().toLowerCase())
	    .setKV("modelPackage", info.getModelPacage())
	    .toFile(path+"\\model\\controller\\"+info.getModel()+"Controller.java")
	    .done();
    	
    	VelocityEngineBuilder.startWithDefault()
	    .withTemplatePath(() -> "vm/modelAddVO.java.vm")
	    .withContextBuild()
	    .setKV("package", info.getPackageName())
	    .setKV("module", info.getModule())
	    .setKV("model", info.getModel())
	    .setKV("lowerModel", info.getModel().toLowerCase())
	    .setKV("modelPackage", info.getModelPacage())
	    .toFile(path+"\\model\\vo\\"+info.getModel()+"AddVO.java")
	    .done();
    	
    	VelocityEngineBuilder.startWithDefault()
	    .withTemplatePath(() -> "vm/modelUpdateVO.java.vm")
	    .withContextBuild()
	    .setKV("package", info.getPackageName())
	    .setKV("module", info.getModule())
	    .setKV("lowerModel", info.getModel().toLowerCase())
	    .setKV("model", info.getModel())
	    .setKV("modelPackage", info.getModelPacage())
	    .toFile(path+"\\model\\vo\\"+info.getModel()+"UpdateVO.java")
	    .done();
    	
    	VelocityEngineBuilder.startWithDefault()
	    .withTemplatePath(() -> "vm/modelVO.java.vm")
	    .withContextBuild()
	    .setKV("package", info.getPackageName())
	    .setKV("module", info.getModule())
	    .setKV("lowerModel", info.getModel().toLowerCase())
	    .setKV("model", info.getModel())
	    .setKV("modelPackage", info.getModelPacage())
	    .toFile(path+"\\model\\vo\\"+info.getModel()+"VO.java")
	    .done();
    	
    	
    	VelocityEngineBuilder.startWithDefault()
	    .withTemplatePath(() -> "vm/serviceImpl.java.vm")
	    .withContextBuild()
	    .setKV("package", info.getPackageName())
	    .setKV("module", info.getModule())
	    .setKV("model", info.getModel())
	    .setKV("modelPackage", info.getModelPacage())
	    .toFile(path+"\\model\\service\\impl\\"+info.getModel()+"ServiceImpl.java")
	    .done();
    	
    	VelocityEngineBuilder.startWithDefault()
	    .withTemplatePath(() -> "vm/modelBO.java.vm")
	    .withContextBuild()
	    .setKV("package", info.getPackageName())
	    .setKV("module", info.getModule())
	    .setKV("lowerModel", info.getModel().toLowerCase())
	    .setKV("model", info.getModel())
	    .setKV("modelPackage", info.getModelPacage())
	    .toFile(path+"\\model\\bo\\"+info.getModel()+"BO.java")
	    .done();
    	
    	
    	
    }
}

