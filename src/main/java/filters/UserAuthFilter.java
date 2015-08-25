package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


public class UserAuthFilter implements Filter {
	private static final Logger log = Logger.getLogger(UserAuthFilter.class);
	private FilterConfig fConfig = null;


	public UserAuthFilter() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String url = req.getServletPath();
		boolean allowedRequest = false;
		String strURL = "";

		String urls = fConfig.getInitParameter("avoidurls");
		String[] urlList = urls.split(",");
		
		for (int i = 0; i < urlList.length; i++) {
			strURL = urlList[i];
			if (url.startsWith(strURL)) {
				allowedRequest = true;
			}
		}

		if (!allowedRequest) {
			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute("login") == null) {
				// Forward the control to login.jsp if authentication fails
				req.getRequestDispatcher("/index.jsp").forward(req, res);
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public synchronized void init(FilterConfig filterConfig) throws ServletException {
		fConfig = filterConfig;
	}

}
