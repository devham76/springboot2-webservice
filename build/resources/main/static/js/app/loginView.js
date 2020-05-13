        // 도메인 devham76.com으로 통일하기 위해 추가한다.
        let host = window.location.host;
        if(host != "devham76.com" && host.indexOf("localhost") <0){
            window.location.href = "https://devham76.com";
        }
        window.location.href = "https://devham76.com";