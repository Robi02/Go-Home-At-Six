var GHASIX_API = {
    // AJAX API Call
    ajaxApiCall : function(httpMethod, apiURL, reqHeader, reqBody, alwaysFunc, doneFunc, failFunc) {
        if (!httpMethod) {
            console.log("Parameter 'httpMethod' warning! (httpMethod:" + httpMethod + ")");
            console.log("'httpMethod' forcibly changed to 'GET'");
            httpMethod = 'GET';
        }

        if (!apiURL) {
            console.log("Parameter 'apiURL' error! (apiURL:" + httpMethod + ")");
            return false;
        }

        $.ajax({
            method : httpMethod,
            url : apiURL,
            headers: reqHeader,
            type : 'json',
            contentType : 'application/json',
            data : reqBody
        })
        .always(function(data_jqXHR, textStatus, jqXHR_errorThrown) {
            if (!!alwaysFunc) return alwaysFunc(data_jqXHR, textStatus, jqXHR_errorThrown);
        })
        .done(function(apiResult, textStatus, jqXHR) {
            if (!!doneFunc) return doneFunc(apiResult, textStatus, jqXHR);
            return true;
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            if (!!failFunc) return failFunc(jqXHR, textStatus, errorThrown);
            return false;
        });
    },
    // API ResultCode Check
    checkResultSuccess : function(apiResult) {
        if (!apiResult) {
            console.log("Parameter 'apiResult' error! (apiResult:" + apiResult + ")");
            return false;
        }

        if (resultData.resultCode != '00000') {
            return false;
        }

        return true;
    },
    // Get ResultData From API Result
    getResultData : function(apiResult, key) {
        if (!apiResult) {
            console.log("Parameter 'apiResult' error! (apiResult:" + apiResult + ")");
            return null;
        }

        if (!key) {
            console.log("Parameter 'key' error! (key:" + key + ")");
            return null;
        }

        var resultData = apiResult.resultData;

        if (!resultData) {
            console.log("Parameter 'resultData' error! (resultData:" + resultData + ")");
            return null;
        }

        return resultData.key;
    }
}