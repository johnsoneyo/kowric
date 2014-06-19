/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Guest
 */
 
public class LoggingInterceptor {
     
  private Logger logger = Logger.getLogger("kowric logs");
@AroundInvoke
public Object logMethod(InvocationContext ic) throws Exception {
logger.entering(ic.getTarget().toString(), ic.getMethod().getName());
 
try {
return ic.proceed();
} finally {
logger.exiting(ic.getTarget().toString(), ic.getMethod().getName());
}
}
 
}
