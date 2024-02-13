![image](https://github.com/gokulkrishnanj/Spring-Boot-Security/assets/102644051/6f891b23-8b8a-4600-84e0-a46c61cafc35)
Step1: User makes a requet
Step2: Enters filter chain
Step3: Filter chain checks for token if token not exists throw 403 forbidden
Step4: If token exist call jwt service to validate token(Jwt service contains getusername,getALlclaims,getSingleClaim,generateToken,Token Validation and other methods)
Step5: if token exists call to jwtService to get username
Step6: If username exists and SecurityContextHolder is not null call filterChain.doFilter(request,response)
Step7: else loadUsername from db and update to securityContextHolder and send to dispatcher servlet and it redirects to controller and sends json response and 200
Step9 In between few configuration to execute the flow to decurity filter chain the configuration is mentioned below 
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf() //to disable csrf
                .disable()
                .authorizeHttpRequests() // to authenticate endpoints
                .requestMatchers("/auth/api/v1/**") // permiting all the request matching this regex pattern
                .permitAll()
                .anyRequest()
                .authenticated() // authenticating all other request
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // maintaining order of filter here our filter configuration should execute first
        return httpSecurity.build();
    }


    Reference: https://www.youtube.com/watch?v=KxqlJblhzfI
    https://spring.io/guides/topicals/spring-security-architecture
