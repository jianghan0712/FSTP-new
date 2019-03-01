from jpype import *
from log.PyPService import *
import os
import __main__


if __main__:
    jarpath = os.path.join(os.path.abspath('.'),'C:\Users\jianghan\Desktop\\fstp-core.jar')
    dependency = os.path.join(os.path.abspath('.'), 'D:/fstp-core_lib/')
    
    startJVM(getDefaultJVMPath(),  
    #          "-XX:+CreateMinidumpOnCrash",
             "-Dsun.java2d.d3d = false",
             "-Xms256m",
             "-XX:NewSize10240k",
             "-Djava.class.path=%s" % jarpath,
             "-Djava.ext.dirs=C:\Program Files\Java\jre1.8.0_131\lib\ext;%s" %dependency,
             "-DServiceName=%s" %"FstpPublishExampleService",
             "-DEnv=DEV",
             "-DInstance=1",
             "-DDevelop=python")
    
    # PService = JClass("com.purefun.fstp.core.server.PService")
    PService = JClass("com.purefun.fstp.core.server.PService")
    a = PService()
    
    a.init()
    a.start()
    factory = a.getRpcfactory()
    pub = factory.createPublisher()
    bo = JClass("com.purefun.fstp.core.bo.otw.QNSRequestBO_OTW")
    # pub.publish(bo())
    # test = JClass("com.purefun.fstp.core.python.TestPython")
    # a = test(1,'123')
    # print a.getA(),a.getB()
    # a.getLog().info("int={},str={}",a.getA(),a.getB())
    #       
    # test.getLog().info("1234")
    # a = PyPService()
    # a.start1()
    
    shutdownJVM()

