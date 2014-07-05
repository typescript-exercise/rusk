describe('suite', function() {
    it('spec', function() {
        
        var sample = new rusk.Sample('test');
        
        expect(sample.getName()).toBe('test');
        
    });
});

