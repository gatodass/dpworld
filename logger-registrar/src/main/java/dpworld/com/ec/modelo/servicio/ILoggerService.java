package dpworld.com.ec.modelo.servicio;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import dpworld.com.ec.modelo.entity.LoggerDp;

@Component
public interface ILoggerService {

	public LoggerDp findById(String uuid);
	public LoggerDp save(LoggerDp loggerDp);
}
