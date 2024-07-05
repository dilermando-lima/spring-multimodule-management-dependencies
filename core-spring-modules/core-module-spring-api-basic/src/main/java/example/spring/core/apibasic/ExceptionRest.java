package example.spring.core.apibasic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionRest.class);

    private ExceptionRestProperties exceptionRestProperties;

    public ExceptionRest(@Autowired ExceptionRestProperties exceptionRestProperties) {
        this.exceptionRestProperties = exceptionRestProperties;
    }

    public static class ApiException extends RuntimeException {

        private final HttpStatusCode httpStatus;
        private final String origin;
        private final String errorCode;

        public ApiException(HttpStatusCode httpStatus, String message, String errorCode, Throwable throwable, String origin) {
            super(message, throwable);
            this.httpStatus = httpStatus;
            this.origin = origin;
            this.errorCode = errorCode;
        }

        public ApiException(HttpStatusCode httpStatus, String message) {
            this(httpStatus, message, null, null, null);
        }

        public ApiException(HttpStatusCode httpStatus, String message, Throwable throwable) {
            this(httpStatus, message, null, throwable, null);
        }

        public ApiException(HttpStatusCode httpStatus, String message, String origin) {
            this(httpStatus, message, null, null, origin);
        }

        public ApiException(HttpStatusCode httpStatus, String message, String errorCode, String origin) {
            this(httpStatus, message, errorCode, null, origin);
        }

        public HttpStatusCode getHttpStatus() {
            return httpStatus;
        }

        public String getOrigin() {
            return origin;
        }

        public String getErrorCode() {
            return errorCode;
        }

    }

    public record ErrorReturn(int status, String error, String origin, String errorCode) {
        public static ResponseEntity<ErrorReturn> getResponse(HttpStatusCode httpStatus, String error, String origin, String errorCode) {
            return new ResponseEntity<>(new ErrorReturn(httpStatus.value(), error, origin, errorCode), httpStatus);
        }
    }

    public enum HttpStatusOnExceptionEnum {

        NULL_POINTER(NullPointerException.class, HttpStatus.INTERNAL_SERVER_ERROR),
        HTTP_MESSAGE_NOT_WRITABLE(HttpMessageNotWritableException.class, HttpStatus.INTERNAL_SERVER_ERROR),
        TYPE_MISMATCH(TypeMismatchException.class, HttpStatus.BAD_REQUEST),
        HTTP_MESSAGE_NOT_READABLE(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST),
        METHOD_ARGUMENT_NOT_VALID(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST),
        BIND(BindException.class, HttpStatus.BAD_REQUEST),
        MISSING_SERVLET_REQUEST_PART(MissingServletRequestPartException.class, HttpStatus.BAD_REQUEST),
        MISSING_SERVLET_REQUEST_PARAMETER(MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST),
        SERVLET_REQUEST_BINDING(ServletRequestBindingException.class, HttpStatus.BAD_REQUEST),
        NO_HANDLER_FOUND(NoHandlerFoundException.class, HttpStatus.NOT_FOUND),
        ASYNC_REQUEST_TIMEOUT(AsyncRequestTimeoutException.class, HttpStatus.SERVICE_UNAVAILABLE),
        HTTP_REQUEST_METHOD_NOT_SUPPORTED(HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED),
        HTTP_MEDIATYPE_NOT_SUPPORTED(HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE),
        HTTP_MEDIATYPE_NOT_ACCEPTABLE(HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE),
        MISSING_PATHVARIABLE(MissingPathVariableException.class, HttpStatus.INTERNAL_SERVER_ERROR),
        ILLEGAL_ARGUMENT_EXCEPTION(IllegalArgumentException.class, HttpStatus.INTERNAL_SERVER_ERROR),
        ;

        private final Class<? extends Throwable> throwableClass;
        private final HttpStatus httpStatus;

        private HttpStatusOnExceptionEnum(Class<? extends Throwable> throwableClass, HttpStatus httpStatus) {
            this.throwableClass = throwableClass;
            this.httpStatus = httpStatus;
        }

        public static HttpStatus getHttpStatusByException(Throwable throwable) {
            if (throwable == null)
                return HttpStatus.INTERNAL_SERVER_ERROR;

            for (var enumException : HttpStatusOnExceptionEnum.values())
                if (enumException.throwableClass.isInstance(throwable))
                    return enumException.httpStatus;

            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    @ExceptionHandler({
            Throwable.class,
            ApiException.class, // aplication generic exception
            ErrorResponseException.class, // above any ResponseStatusException.class
            NullPointerException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            MissingServletRequestPartException.class,
            NoHandlerFoundException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            BindException.class,
            IllegalArgumentException.class,
            AsyncRequestTimeoutException.class
    })
    public ResponseEntity<ErrorReturn> handleThrowableException(Throwable throwable) {

        final HttpStatusCode status;
        String origin = null;
        String errorCode = null;
        String error = null;

        if (throwable instanceof ApiException exception) {
            status = exception.getHttpStatus();
            error = exception.getMessage();
            origin = exception.getOrigin();
            errorCode = exception.getErrorCode();
        } else if (throwable instanceof ErrorResponseException exception) {
            status = HttpStatus.valueOf(exception.getStatusCode().value());
            error = exception.getMessage();
        } else {
            status = HttpStatusOnExceptionEnum.getHttpStatusByException(throwable);
            error = throwable.getMessage();
        }

        origin = origin == null ? exceptionRestProperties.getApplicationOriginDefault() : origin;

        if(status == HttpStatus.BAD_REQUEST && exceptionRestProperties.isEnableLogBadRequestMessage()){
            LOGGER.error(error);
        }else if(status == HttpStatus.INTERNAL_SERVER_ERROR && exceptionRestProperties.isEnableLogInternalServerErrorStrace()){
            LOGGER.error(error, throwable);
        }

        return ErrorReturn.getResponse(status, error, origin, errorCode);
    }

}