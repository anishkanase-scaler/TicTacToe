package com.example.tictactoe.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            // preflight request, now frontend will send this to backend just to check if it allows it to send requests, this doesn't need any authorization, if this req doesn't go, nthg else can be done. So, no CORS checking we do here.

            // Now OPTIONS is a type of http request jus like get, put, patch, post etc. React is sending this to check backend. This is preflight
            if (request.getMethod().equals("OPTIONS")) return true;


            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                // convert string to UUID, userId is the token in UUID form
                UUID userId = UUID.fromString(token);

                // Attach this to request so that our controller cna use it later.
                request.setAttribute("userId", userId);
                // this is key, value
                // in controller we use this as  userId = request.getAttribute("userId")

                return true;
            }
        } catch (IllegalArgumentException e) {
            // the token was malformed, not a valid UUID string
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        // if auth header was null or not bearer, frontend will send us to login page
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}




// So we are creating this middleware because currently, in our APIs, we are taking IDs directly from the path variables sent by the frontend. Now, a user can easily change these IDs in the URL and send any other user ID or game ID. For example, a user could try to access or join a game that they are not supposed to. This is what we want to prevent.
//
//So what we will do is create a security layer between the frontend and the controller. The frontend will no longer directly reach the controller. Every request will first go through the middleware, which will check whether the user is authenticated or not.
//
//Now, when a user logs in or signs in, the backend will verify their password against the hashed password stored in the database. If the credentials are correct, the backend will generate an authentication token. This token will be sent to the frontend and stored in the browser.
//
//Whenever the frontend makes another API request, it will send this token in the Authorization header. Now, this request will first reach our middleware.
//
//The middleware will receive the token from the Authorization header and verify whether it is valid. If the token is valid, the middleware will identify the user and attach their user information to the request. Then, the request will move forward to the controller.
//
//But if the token is invalid or expired, the middleware will stop the request and return an unauthorised response. The frontend can then redirect the user to the login page.
//
//Also, whenever a user tries to access a particular game or another user's data, we will use the authenticated user's ID to check whether they actually have permission to access that resource. This way, simply changing an ID in the URL will not allow them to access someone else's data.
//
//So that is how we are creating a security layer between the frontend and the controller.