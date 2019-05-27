var GHASIX_API = {
    // API URL
    apiURL : {
        selectById        : '/commutes/{0}',
        selectAll         : '/commutes/time/all',
        selectByTime      : '/commutes/time/{0}-{1}',
        selectLastCheckIn : '/commutes/last',
        checkIn           : '/commutes',
        checkOut          : '/commutes/{0}',
        modifyById        : '/commutes/{0}',
        deleteById        : '/commutes/{0}'
    },

    // AJAX API Call
    apiAjaxCall : function(httpMethod, apiURL, reqHeader, reqBody, alwaysFunc, doneFunc, failFunc) {
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
            data : JSON.stringify(reqBody)
        })
        .always(function(data_jqXHR, textStatus, jqXHR_errorThrown) {
            if (!!alwaysFunc) return alwaysFunc(data_jqXHR, textStatus, jqXHR_errorThrown);
        })
        .done(function(data, textStatus, jqXHR) {
            if (!!doneFunc) return doneFunc(data.apiResult, textStatus, jqXHR);
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

        if (apiResult.resultCode != '00000') {
            return false;
        }

        return true;
		},
    // Get ResultData From API ResultData
    getResultData : function(apiResult, key) {
        if (!apiResult) {
            console.log("Parameter 'apiResult' error! (apiResult:" + apiResult + ")");
            return null;
        }

        if (!key) {
            console.log("Parameter 'key' error! (key:" + key + ")");
            return null;
        }

        var resultData = apiResult.resultDatas;

        if (!resultData) {
            console.log("Parameter 'resultData' error! (resultData:" + resultData + ")");
            return null;
        }

        return resultData[key];
    }
}