package dpworld.com.ec.modelo.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dpworld.com.ec.modelo.dao.ILoggerDao;
import dpworld.com.ec.modelo.entity.LoggerDp;
import jakarta.transaction.Transactional;

@Service
public class LoggerServiceImpl implements ILoggerService {

	
	@Autowired
	private ILoggerDao iLoggerDao;
	
	@Transactional
	public LoggerDp findById(Long id) {
		// TODO Auto-generated method stub
		return iLoggerDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public LoggerDp save(LoggerDp loggerDp) {
		// TODO Auto-generated method stub
		return iLoggerDao.save(loggerDp);
	}

}
