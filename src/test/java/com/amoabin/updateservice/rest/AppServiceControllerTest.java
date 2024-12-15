//package com.amoabin.updateservice.rest;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AppServiceControllerTest {
//
//    @Autowired
//    private AppServiceController appServiceController;
//
//    // Test cases will be added her
//
//
//    @Test
//    public void testUpdateWithRestrictedRole() {
//        // Prepare the input and expected output for the test
//        // ...
//
//        // Create a list of granted authorities (roles)
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_RESTRICTED"));
//
//        // Mock the authentication object with the granted authorities
//        Authentication authentication = Mockito.mock(Authentication.class);
//        Mockito.when(authentication.getAuthorities()).thenReturn(grantedAuthorities);
//
//        // Set the mocked authentication object in the SecurityContextHolder
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Call the update method of the controller
//        String result = appServiceController.update(input);
//
//        // Assert that the expected result is returned
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    public void testUpdateWithNonRestrictedRole() {
//        // Prepare the input and expected output for the test
//        // ...
//
//        // Create a list of granted authorities (roles)
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_NON_RESTRICTED"));
//
//        // Mock the authentication object with the granted authorities
//        Authentication authentication = Mockito.mock(Authentication.class);
//        Mockito.when(authentication.getAuthorities()).thenReturn(grantedAuthorities);
//
//        // Set the mocked authentication object in the SecurityContextHolder
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Call the update method of the controller
//        String result = appServiceController.update(input);
//
//        // Assert that the expected result is returned
//        assertEquals(expectedResult, result);
//    }
//
//    {
//    }
//}
