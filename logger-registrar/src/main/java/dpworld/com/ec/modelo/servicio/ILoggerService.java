package dpworld.com.ec.modelo.servicio;

import org.springframework.stereotype.Component;

import dpworld.com.ec.modelo.entity.LoggerDp;

@Component
public interface ILoggerService {

	public LoggerDp findById(Long id);
	public LoggerDp save(LoggerDp loggerDp);
}
