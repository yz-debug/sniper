/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Create Date : 2015-5-11
 */

package org.workin.security.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description 自定义的Shiro会话DAO实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CustomSessionDAO extends AbstractSessionDAO {
	
	@Autowired
	private SessionRepository sessionRepository;  
	
	public SessionRepository getSessionRepository() {
		return sessionRepository;
	}

	public void setSessionRepository(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		sessionRepository.saveSession(session);
	}

	@Override
	public void delete(Session session) {
		Serializable sessionId;
		if (session != null && (sessionId = session.getId()) != null)
			sessionRepository.deleteSession(sessionId);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return sessionRepository.getAllSessions();
	}

	@Override
	protected Serializable doCreate(Session session) {
		 Serializable sessionId = this.generateSessionId(session);  
		 this.assignSessionId(session, sessionId);  
		 sessionRepository.saveSession(session);  
		 return sessionId;  
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return sessionRepository.getSession(sessionId);
	}

}
