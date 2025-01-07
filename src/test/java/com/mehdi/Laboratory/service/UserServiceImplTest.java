package com.mehdi.Laboratory.service;

import com.mehdi.Laboratory.domain.JpaUser;
import com.mehdi.Laboratory.dto.register.AddUserReqDTO;
import com.mehdi.Laboratory.dto.register.AddUserResDTO;
import com.mehdi.Laboratory.repository.UserRepository;
import com.mehdi.Laboratory.shared.constants.ResponseCodePool;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {


    @InjectMocks // Injects Mocked dependencies like userRepository to this class
    private UserServiceImpl userService;

    @Mock // creates a mock object for specified class - useful for DI in tests
    private UserRepository userRepository;

//    @Spy // Opposite to mock - it calls the real methods of annotated class - unless you explicitly mock it ( using when or doReturn or doThrow or ... )
//    private UserController userController;


    @BeforeAll // Runs once before all tests
    static void beforeAll() {
        System.out.println("Running Tests");
    }


    @AfterAll
    static void afterAll() {
        System.out.println("End Running Tests");
    }

    @BeforeEach // Runs before each test method.
    void beforeEach() {
//        this can be replaced with ExtendWith as a modern solution
//        MockitoAnnotations.openMocks(this); // this will initialize the mock and inject dependencies annotated with @mock
    }

    @AfterEach // Runs After Each Test
    void afterEach() {

    }

    @DisplayName("Test Saving Behaviour of save method from UserService Class") // Provides a name usable for better reporting
    @Test // Marks a method as a test case
    void saveShouldSaveUser() {
        // Arrangement
        AddUserReqDTO addUserReqDTO = new AddUserReqDTO("testusername", "testfname", "testlname", "securepasspharse", 1254594544L);

        // Acting
        JpaUser mockedUser = new JpaUser(addUserReqDTO.getUsername());
        when(userRepository.save(any())).thenReturn(mockedUser);
        AddUserResDTO savedUser = userService.save(addUserReqDTO);

        // Assertion
        assertNotNull(savedUser);
        assertEquals(ResponseCodePool.SUCCESS.getMessage(), savedUser.getMessage());
        assertEquals(addUserReqDTO.getUsername(), savedUser.getUsername());
    }

    @Tag("birthDate") // useful for running specific type of tests
    @Test
    void testFutureBirthdate() {
        // Arrangement
        AddUserReqDTO addUserReqDTO = new AddUserReqDTO("testusername", "testfname", "testlname", "securepasspharse", 1752874233572L);

        // Acting
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.save(addUserReqDTO));
        assertEquals("Birth date must be in the past", runtimeException.getMessage());
    }

    @Tag("birthDate")
    @Test
    void testBirthdateBefore1920() {
        // Arrangement
        AddUserReqDTO addUserReqDTO = new AddUserReqDTO("testusername", "testfname", "testlname", "securepasspharse", -1649119418L);

        // Acting
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.save(addUserReqDTO));
        assertEquals("Birth date must be bigger than 1920", runtimeException.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1649119418, Birth date must be bigger than 1920",  // past
            "1752874233572, Birth date must be in the past" // future
    })
    void testBirthdateComplete(Long birthDate, String exceptionMessage) {
        // Arrangement
        AddUserReqDTO request = new AddUserReqDTO("testusername", "testfname", "testlname", "securepasspharse", birthDate);

        // Acting / Assertions
        if (StringUtils.isNotBlank(exceptionMessage)) {
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.save(request));
            assertEquals(exceptionMessage, runtimeException.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForBirthDate")
    void testBirthdateCompleteWithMethodSource(Long birthDate, String expectedMessage) {
        // Arrangement
        AddUserReqDTO request = new AddUserReqDTO("testusername", "testfname", "testlname", "securePass123", birthDate);

        // Acting / Assertions
        if (StringUtils.isNotBlank(expectedMessage)) {
            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> userService.save(request));

            // Using junit Assertion
            assertEquals(expectedMessage, runtimeException.getMessage());

            // Using AssertJ
            assertThat(runtimeException.getMessage()).isEqualTo(expectedMessage);

            // Using hamcrest -> can not used beside assertJ - check dependencies
//            assertThat(runtimeException.getMessage(), equalTo(expectedMessage));
        }
    }

    private static Stream<Arguments> provideTestCasesForBirthDate() {
        return Stream.of(
                Arguments.of("-1649119418", "Birth date must be bigger than 1920"),
                Arguments.of("1752874233572", "Birth date must be in the past")
        );
    }


    @Disabled("Feature has not implemented yet")
    @Test
    void testFatherName() {

    }

}

//Common Assertions in AssertJ
//	1.	Equality Assertions:
//  •	assertThat(actual).isEqualTo(expected);
//	•	assertThat(actual).isNotEqualTo(expected);
//	•	assertThat(actual).isSameAs(expected);
//	2.	Null Assertions:
//  •	assertThat(actual).isNull();
//	•	assertThat(actual).isNotNull();
//	3.	Boolean Assertions:
//  •	assertThat(actual).isTrue();
//	•	assertThat(actual).isFalse();
//	4.	String Assertions:
//  •	assertThat(actual).startsWith("prefix");
//	•	assertThat(actual).endsWith("suffix");
//	•	assertThat(actual).contains("substring");
//	•	assertThat(actual).matches("regexPattern");
//	5.	Collection Assertions:
//  •	assertThat(list).hasSize(3);
//	•	assertThat(list).contains("element");
//	•	assertThat(list).doesNotContain("element");
//	•	assertThat(list).containsExactly("elem1", "elem2");
//	6.	Exception Assertions:
//  •	assertThatThrownBy(() -> method()).isInstanceOf(SomeException.class);
//	7.	Date Assertions:
//  •	assertThat(date).isBefore(someDate);
//	•	assertThat(date).isAfter(someDate);
//	8.	Numeric Assertions:
//  •	assertThat(number).isPositive();
//	•	assertThat(number).isNegative();
//	•	assertThat(number).isGreaterThan(10);
//	9.	Failing Assertions:
//  •	assertThatThrownBy(() -> method()).hasMessage("message");
//	•	assertThatThrownBy(() -> method()).isInstanceOf(Exception.class).hasMessageContaining("message");