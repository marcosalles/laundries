package actions;

import daos.UserDAO;
import models.RequestLog;
import models.User;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class RequestLogAction extends Action.Simple {

	@Inject
	private UserDAO userDAO;

	@Override
	public CompletionStage<Result> call(Context context) {
		String code = context.request().getHeader("API-Token");
		User user = userDAO.withToken(code).get();
		String ip = context.request().remoteAddress();
		String uri = context.request().uri();
		RequestLog log = new RequestLog(user, ip, uri);
		log.save();
		return delegate.call(context);
	}

}
