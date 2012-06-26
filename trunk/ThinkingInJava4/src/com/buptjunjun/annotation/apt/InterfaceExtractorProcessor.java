package com.buptjunjun.annotation.apt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.sun.mirror.*;
import com.sun.mirror.apt.*;
import com.sun.mirror.declaration.*;

public class InterfaceExtractorProcessor implements AnnotationProcessor{

	private final AnnotationProcessorEnvironment env ;
	
	private List<MethodDeclaration> interfaceMethod = new ArrayList<MethodDeclaration>();
	
	public InterfaceExtractorProcessor(AnnotationProcessorEnvironment env)
	{
		this.env = env;
	}
	
	
	@Override
	public void process() {
		// TODO Auto-generated method stub
		for(TypeDeclaration typeDecl: env.getSpecifiedTypeDeclarations())
		{
			ExtractInterface annot = typeDecl.getAnnotation(ExtractInterface.class);
			
			if (annot == null) break;
			
			for (MethodDeclaration m: typeDecl.getMethods())
			{
				if (m.getModifiers().contains(Modifier.PUBLIC) && !m.getModifiers().contains(Modifier.STATIC) )
				{
					this.interfaceMethod.add(m);
				}
				
			}
			
			if(this.interfaceMethod.size() > 0)
			{
				try
				{
					PrintWriter writer = env.getFiler().createSourceFile(annot.value());
					
					// write package
					writer.println("package " + typeDecl.getPackage().getQualifiedName()+" ;");
					
					writer.println("public interface " + annot.value() +"\n{");
					
					for(MethodDeclaration m : this.interfaceMethod)
					{
						writer.print("public " + m.getReturnType()+" "+ m.getSimpleName() +" (");
						
						int i = 0;
						for(ParameterDeclaration p : m.getParameters())
						{
							if(i < m.getParameters().size())
								writer.print(p.getType() + " "+ p.getSimpleName()+" ,");
							else
								writer.print(p.getType() + " "+ p.getSimpleName()+" )");
							i++;
						}
						
					}
					
					writer.println("}");
					writer.close();
					
					
				}
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

}
