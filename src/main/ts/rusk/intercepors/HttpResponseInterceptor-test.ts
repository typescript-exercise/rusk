describe('HttpResponseInterceptor のテスト', () => {
    var interceptor;
    var _$q;
    var _$location;
    
    var RESPONSE = {
        BAD_REQUEST: {
            status: 400,
            config: {}
        },
        
        NOT_FOUND: {
            status: 404,
            config: {}
        },
        
        INTERNAL_SERVER_ERROR: {
            status: 500,
            config: {}
        }
    };

    beforeEach(() => {
        module('rusk');
        inject((_HttpResponseInterceptor_, $q, $location) => {
            interceptor = _HttpResponseInterceptor_;
            _$q = $q;
            _$location = $location;
        });
        
        spyLocationPathMethod(_$location);
    });

    it('レスポンスコードが 400 の場合、不正アクセスページへ強制遷移させる', () => {
        // exercise
        interceptor.responseError(RESPONSE.BAD_REQUEST);
        
        // verify
        expect(_$location.path).toHaveBeenCalledWith('/bad-request');
    });

    it('レスポンスコードが 404 の場合、ページが存在しないことを示すページへ強制遷移させる', () => {
        // exercise
        interceptor.responseError(RESPONSE.NOT_FOUND);
        
        // verify
        expect(_$location.path).toHaveBeenCalledWith('/not-found');
    });

    it('レスポンスコードが 500 の場合、サーバーエラーページへ強制遷移させる', () => {
        // exercise
        interceptor.responseError(RESPONSE.INTERNAL_SERVER_ERROR);
        
        // verify
        expect(_$location.path).toHaveBeenCalledWith('/server-error');
    });

    it('インターセプターの上書き処理が定義されている場合、既存の処理は実行されず、上書きした処理が呼び出されること', () => {
        // setup
        var override = jasmine.createSpyObj('overrideInterceptor', [404]);
        var extendConfig = {
            overrideInterceptor: override
        };
        
        var response = $.extend({}, RESPONSE.NOT_FOUND, {config: extendConfig});
        
        // exercise
        interceptor.responseError(response);
        
        // verify
        expect(_$location.path.calls.count()).toBe(0);
        expect(override[404]).toHaveBeenCalled();
    });
    
    function spyLocationPathMethod($location) {
        spyOn($location, 'path').and.callThrough();
    }
});