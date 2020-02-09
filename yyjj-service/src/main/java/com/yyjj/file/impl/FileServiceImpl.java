package com.yyjj.file.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyjj.bo.file.FileLibraryBO;
import com.yyjj.constant.file.FileCategory;
import com.yyjj.constant.file.FileConstant.FileDownloadConstant;
import com.yyjj.constant.file.FileConstant.PathConstant;
import com.yyjj.file.FileLibraryService;
import com.yyjj.file.FileService;
import com.yyjj.model.file.FileLibrary;

@Service
public class FileServiceImpl implements FileService{
	private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

	@Autowired
	FileLibraryService fileLibraryService;
	static final Random  RANDOM = new Random();
	
	@Value("${upload.fileLibraryPath}")
	String savePath;
	@Value("${upload.visitPath}")
	String visitPath;
	
	
	private String createFileName() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");	
		return LocalDateTime.now().format(formatter)+String.valueOf(RANDOM.nextInt(100));
	}
	@Override
	public FileLibrary upload(MultipartFile file,Integer fileLibraryId,Integer type) throws Exception {
		FileLibrary fileLibrary = fileLibraryService.getById(fileLibraryId);
		if(Objects.isNull(fileLibrary)) {
			throw new FileUploadException("文件上传失败");
		}
		String fileName = file.getOriginalFilename();//上传时文件名
		String suffix = fileName.substring(fileName.lastIndexOf("."));//文件后缀
		//String prefix = fileName.substring(fileName.lastIndexOf("."));//截取文件格式
		//拼接当前时间
		//fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + prefix;
		//文件路劲
		String trueFileName = createFileName()+suffix;
		StringBuffer pathBuffer =  new StringBuffer(fileLibrary.getFilePath());
		pathBuffer.append(File.separator);
		pathBuffer.append(trueFileName);	
		//创建新文件
		File dest = new File(savePath + pathBuffer);
		if (!dest.getParentFile().exists()) {			
			//创建文件夹
			dest.getParentFile().mkdirs();
		}
		file.getSize();
		file.transferTo(dest);
		
		// 插入表信息 返回表主键id
		FileLibrary newFile = new FileLibrary(fileLibrary.getProjectId(), fileLibrary.getId(),file.getSize(), fileName,pathBuffer.toString(),trueFileName);
		newFile.setFileType(type);
		//创建人
		newFile.setUpdateTime(LocalDateTime.now());
		fileLibraryService.save(newFile);
		newFile.setFileDownUrl(visitPath+File.separator+FileDownloadConstant.FILE+File.separator+newFile.getId()+File.separator+FileDownloadConstant.DOWNLOAD);
		fileLibraryService.updateById(newFile);
		return newFile;
	
	}
	
	/**
	 * 上传用户头像
	 * @param file
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	public FileLibrary uploadIcon(MultipartFile file,Integer type) throws Exception {
//		FileLibrary fileLibrary = fileLibraryService.getById(fileLibraryId);
//		if(Objects.isNull(fileLibrary)) {
//			throw new FileUploadException("文件上传失败");
//		}
		String fileName = file.getOriginalFilename();//上传时文件名
		String suffix = fileName.substring(fileName.lastIndexOf("."));//文件后缀
		
		//String prefix = fileName.substring(fileName.lastIndexOf("."));//截取文件格式
		//拼接当前时间
		//fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + prefix;
		//文件路劲
		String trueFileName = createFileName()+suffix;
		StringBuffer pathBuffer =  new StringBuffer(FileCategory.ICON.getPath());
		pathBuffer.append(File.separator);
		pathBuffer.append(trueFileName);
		String filePath = pathBuffer.toString();
		//创建新文件
		File dest = new File(savePath + pathBuffer);
		if (!dest.getParentFile().exists()) {			
			//创建文件夹
			dest.getParentFile().mkdirs();
		}
		file.getSize();
		file.transferTo(dest);
		
		// 插入表信息 返回表主键id
		FileLibrary newFile = new FileLibrary(file.getSize(), fileName,filePath,trueFileName);
		newFile.setFileType(type);
		//创建人
		newFile.setUpdateTime(LocalDateTime.now());
		fileLibraryService.save(newFile);
		newFile.setFileDownUrl(visitPath+File.separator+FileDownloadConstant.FILE+File.separator+newFile.getId()+File.separator+FileDownloadConstant.DOWNLOAD);
		fileLibraryService.updateById(newFile);
		return newFile;
	}
	
	@Override
	public FileLibrary upload(MultipartFile file,Integer type,FileCategory fileCategory) throws Exception {
		
		String fileName = file.getOriginalFilename();//上传时文件名
		String suffix = fileName.substring(fileName.lastIndexOf("."));//文件后缀
		
		//String prefix = fileName.substring(fileName.lastIndexOf("."));//截取文件格式
		//拼接当前时间
		//fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + prefix;
		//文件路劲
		String trueFileName = createFileName()+suffix;
		StringBuffer pathBuffer =  new StringBuffer(fileCategory.getPath());
		pathBuffer.append(File.separator);
		pathBuffer.append(trueFileName);	
		//创建新文件
		File dest = new File(savePath+pathBuffer);
		if (!dest.getParentFile().exists()) {			
			//创建文件夹
			dest.getParentFile().mkdirs();
		}
		file.getSize();
		file.transferTo(dest);
		
		// 插入表信息 返回表主键id
		FileLibrary newFile = new FileLibrary(0,0,file.getSize(), fileName,pathBuffer.toString(),trueFileName);
		newFile.setFileType(type);
		//创建人
		//newFile.setCreateUser(projectService.getById(fileLibrary.getProjectId()).getFollowUpName());
		newFile.setUpdateTime(LocalDateTime.now());
		fileLibraryService.save(newFile);
		newFile.setFileDownUrl(visitPath+File.separator+FileDownloadConstant.FILE+File.separator+newFile.getId()+File.separator+FileDownloadConstant.DOWNLOAD);
		fileLibraryService.updateById(newFile);
		return newFile;	
	}
	
	/**
	 * 递归检索文件并创建
	 * @param path
	 * @return
	 */
	private File recursionMkdir(File path) {
		if(! path.getParentFile().exists()) {
			String subPath = path.getPath();
			File file = new File(subPath.substring(0,subPath.lastIndexOf(File.separator)));
			recursionMkdir(file);
		}
		path.mkdir();
		return path;
	}
	
	/**
	 * 初始化项目文件库
	 */
	@Override
	public FileLibrary initFileLibrary(Integer projectId) {
		File dest = new File(savePath +FileCategory.ROOT.getPath()+File.separator+ projectId);
		FileLibrary rootFile = null;
		recursionMkdir(dest);
		if (dest.exists()) {
		
				//根目录
				rootFile = new FileLibrary(projectId, 0,String.valueOf(projectId), dest.getPath().substring(savePath.length()));
				fileLibraryService.save(rootFile);
				
				//项目文件
				String projectPath = dest.getPath() + FileCategory.PROJECT.getPath();
				FileLibrary projectFile = new FileLibrary(projectId, rootFile.getId(),FileCategory.PROJECT.getName(), projectPath.substring(savePath.length()));
				if (new File(projectPath).mkdir()) {
					fileLibraryService.save(projectFile);
					initProjectSubFileLibrary(projectId,projectFile.getId(),projectPath);
				}
				
				//投后文件
				String castEnd = dest.getPath() + FileCategory.CAST_END.getPath();
				FileLibrary castEndFile = new FileLibrary(projectId, rootFile.getId(),FileCategory.CAST_END.getName(), castEnd.substring(savePath.length()));
				if (new File(castEnd).mkdir()) {
					fileLibraryService.save(castEndFile);
					initCastEndSubFileLibrary(projectId,castEndFile.getId(),castEnd);
				}

				//其他文件
				String other = dest.getPath() + FileCategory.OTHER.getPath();
				FileLibrary otherFile = new FileLibrary(projectId, rootFile.getId(),FileCategory.OTHER.getName(),other.substring(savePath.length()));
				if (new File(other).mkdir()) {
					fileLibraryService.save(otherFile);
					//initProjectSubFileLibrary(projectId,otherFile.getId(),other);
				}
		}
		return rootFile;
	}
	
	/**
	 * 初始化投后文件库
	 * @param projectId
	 * @return
	 */
	@Override
	public FileLibrary initCastEndMkdir(Integer projectId,Integer afterProjectId) {
		FileLibrary fileLibrary = fileLibraryService.lambdaQuery().eq(FileLibrary::getProjectId,projectId)
					.eq(FileLibrary::getParentId,PathConstant.ROOT_NUM).one();
		fileLibrary = fileLibraryService.lambdaQuery().eq(FileLibrary::getParentId, fileLibrary.getId())
			.eq(FileLibrary::getFileName, FileCategory.CAST_END.getName()).one();
		fileLibrary = fileLibraryService.lambdaQuery().eq(FileLibrary::getParentId, fileLibrary.getId())
			.eq(FileLibrary::getFileName, FileCategory.FINANCIAL_INFORMATION.getName()).one();
		
		
		String superPath =savePath 
				
				+FileCategory.ROOT.getPath()
				+File.separator
				+ projectId
				+File.separator
				+FileCategory.CAST_END.getPath()
				
				+FileCategory.FINANCIAL_INFORMATION.getPath();
		log.info(superPath);
		LocalDateTime localDateTime = null ;
		for (int i = 1; i <= 3; i++) {
			//创建年份文件夹
			localDateTime = LocalDateTime.now().minusYears(i);
			String yearPath = superPath+File.separator+localDateTime.getYear();
			log.info(yearPath);
			if(new File(yearPath).mkdir()) {
				FileLibrary parentfile = new FileLibrary(projectId,fileLibrary.getId(),String.valueOf(localDateTime.getYear()),yearPath.substring(savePath.length()));
				fileLibraryService.save(parentfile);
			 
			 	//创建季度文件夹
//			 	String Q1Path = yearPath+File.separator+PathConstant.Q1;
//			 	String Q2Path = yearPath+File.separator+PathConstant.Q2;
//			 	String Q3Path = yearPath+File.separator+PathConstant.Q3;
//			 	String Q4Path = yearPath+File.separator+PathConstant.Q4;
//			 	String yPath = yearPath+File.separator+PathConstant.YEAY;
			 	//创建财报信息表
			 		
			}
			
		}		
		return fileLibrary;
	}
	
	/**
	 * 创建子分类文件夹
	 * @param projectId
	 * @param superId
	 * @param superPath
	 */
	
	private void initProjectSubFileLibrary(Integer projectId,Integer superId,String superPath) {
		String Path = superPath + FileCategory.INTERVIEW_RECORD.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.INTERVIEW_RECORD.getName(),Path.substring(savePath.length())));
		}
		Path = superPath + FileCategory.BP.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.BP.getName(),Path.substring(savePath.length())));
		}
		
	    Path = superPath + FileCategory.TS_PROTOCOL.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.TS_PROTOCOL.getName(),Path.substring(savePath.length())));
		}
		
		Path = superPath + FileCategory.DUE_DILIGENCE_RESOURCE.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.DUE_DILIGENCE_RESOURCE.getName(),Path.substring(savePath.length())));
		}
		
		Path = superPath + FileCategory.DUE_DILIGENCE_REPORT.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.DUE_DILIGENCE_REPORT.getName(),Path.substring(savePath.length())));
		}
		
		Path = superPath + FileCategory.SECRECY_PROTOCOL.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.SECRECY_PROTOCOL.getName(),Path.substring(savePath.length())));
		}
		
		Path = superPath + FileCategory.DELIVERY_PROTOCOL.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.DELIVERY_PROTOCOL.getName(),Path.substring(savePath.length())));
		}
		
		Path = superPath + FileCategory.MONEY_TO_PROVE.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.MONEY_TO_PROVE.getName(),Path.substring(savePath.length())));
		}
		
		Path = superPath + FileCategory.CHANGE_PROVE.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.CHANGE_PROVE.getName(),Path.substring(savePath.length())));
		}	
	}
	
	private void initCastEndSubFileLibrary(Integer projectId,Integer superId,String superPath) {
		String Path = superPath + FileCategory.FINANCIAL_INFORMATION.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.FINANCIAL_INFORMATION.getName(),Path.substring(savePath.length())));
		}
		
	    Path = superPath + FileCategory.FINANCIAL_STATEMENT.getPath();
		if(new File(Path).mkdir()) {
			fileLibraryService.save(new FileLibrary(projectId,superId,FileCategory.FINANCIAL_STATEMENT.getName(),Path.substring(savePath.length())));
		}

	}
	
	
	
	@Override
	public FileLibraryBO fileLibraryDetail(Integer projectId) {
		List<FileLibrary> files = fileLibraryService.lambdaQuery().eq(FileLibrary::getProjectId, projectId).list();
		List<FileLibraryBO> fileBOs =  new ArrayList<>();
		for (FileLibrary fileLibrary : files) {
			fileBOs.add(FileLibraryBO.newInstance(fileLibrary));
		}
		FileLibrary fileLibrary = fileLibraryService.lambdaQuery().eq(FileLibrary::getProjectId, projectId)
				.eq(FileLibrary::getParentId, 0).one();
		FileLibraryBO vo = FileLibraryBO.newInstance(fileLibrary);
		if(Objects.isNull(vo)) {
			return new FileLibraryBO();
		}
		vo.setSubFileLibraryBOs(getChild(vo.getId(), fileBOs));
		
		return vo;
	}
	
	/**
	 * 根据指定id查找 其下所有子节点
	 * 参数：id
	 *参数：查询集合
	 * @param id
	 * @param list
	 * @return
	 */		
	private static  List<FileLibraryBO> getChild(int id,List<FileLibraryBO> list){
		
		List<FileLibraryBO> childs = new ArrayList<>();
		for (FileLibraryBO fileLibrary : list) {
			if(fileLibrary.getParentId() == 0) {
				//如果是一级节点 跳过
				continue;
			}
			if(id == fileLibrary.getParentId()) {					
				//递归查找子节点
				fileLibrary.setSubFileLibraryBOs(getChild(fileLibrary.getId(), list));					
				//加入返回的集合
				childs.add(fileLibrary);
			}
		}
		return childs;
	}

	@Override
	public Long upload(String path,String content) throws Exception {
		File file = new File(path);
		if(! file.exists()) {
			OutputStream out =null;		
				out=new FileOutputStream(file);  //打开文件输出流
				byte[] bytes= content.trim().getBytes();  //读取输出流中的字节
				out.write(bytes);     //写入文件					
				out.close();  //关闭输出文件流	
				//变更状态
					
			//TODO 操作日志	
		}else {
			throw new FileUploadException("文件上传失败");
		}
		return file.getFreeSpace();
	}
	
	
	@Override
	public void download(String path, OutputStream out) throws Exception {
		FileInputStream inputStream = new FileInputStream(path);
	
		byte buffer[] = new byte[1024];
		
		int len = 0;
		
		while((len = inputStream.read(buffer))>0) {
			out.write(buffer,0,len);
			out.flush();
		}
		
		inputStream.close();
		out.close();
	}

	@Override
	public Boolean removeFileLibrary(Integer projectId) {
		
		FileLibrary fileLibrary = fileLibraryService.lambdaQuery().eq(FileLibrary::getProjectId, projectId)
			.eq(FileLibrary::getParentId, 0).one();
		fileLibraryService.remove(new QueryWrapper<FileLibrary>().lambda().eq(FileLibrary::getProjectId, projectId));
		String pathString =null;
		if(Objects.nonNull(fileLibrary)) {
			pathString = savePath+fileLibrary.getFilePath();
		}else {
			pathString = savePath+FileCategory.ROOT.getPath()+File.separator+projectId;
		}
		File file = new File(pathString);		
		
		
		return delFile(file);
	}
	
	
	public Boolean removeFile(FileLibrary fileLibrary) {
		return removeFile(fileLibrary.getFilePath());									
	}
	
	@Override
	public Boolean removeFile(String  filePath) {
		String pathString =null;
		if(Objects.nonNull(filePath)) {
			pathString = savePath+filePath;
			File file = new File(pathString);
			return delFile(file);
		}
		return false;								
	}
	
	private boolean delFile(File file) {
		if (!file.exists()) {
			  return false;
		}
			  
		 if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
			     delFile(f);
			}
		 }
		return file.delete();
	}

	
	
}
