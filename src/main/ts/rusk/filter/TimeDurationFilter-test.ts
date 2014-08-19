describe('TimeDurationFilterのテスト', () => {
    var filter;

    beforeEach(() => {
        module('rusk');
        inject($filter => {
            filter = $filter('timeDuration');
        });
    });

    it('90000000 を渡した場合、 25:00:00 が返されること', () => {
        // exercise
        var filtered = filter(90000000);
        
        // verify
        expect(filtered).toBe('25:00:00');
    });
    
    it('999 を渡した場合、 00:00:00 が返されること', () => {
        // exercise
        var filtered = filter(999);
        
        // verify
        expect(filtered).toBe('00:00:00');
    });
    
    it('1000 を渡した場合、 00:00:01 が返されること', () => {
        // exercise
        var filtered = filter(1000);
        
        // verify
        expect(filtered).toBe('00:00:01');
    });
    
    it('60000 を渡した場合、 00:01:00 が返されること', () => {
        // exercise
        var filtered = filter(60000);
        
        // verify
        expect(filtered).toBe('00:01:00');
    });
    
    it('80152000 を渡した場合、 22:15:52 が返されること', () => {
        // exercise
        var filtered = filter(80152000);
        
        // verify
        expect(filtered).toBe('22:15:52');
    });
    
});