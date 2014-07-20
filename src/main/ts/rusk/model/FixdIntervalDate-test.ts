describe('Dateのテスト', () => {

    beforeEach(() => {
    });
    
    it('分が 0 より大きく 30 より小さい場合、30 分に切り上げられること', () => {
        // exercise
        var d = new Date(2014, 6, 15, 12, 15);
        var date = new rusk.model.FixdIntervalDate(d);
        
        // verify
        expect(date.toString()).toBe('2014/07/15 12:30');
    });
    
    it('分が 0 の場合、切り上げは行われないこと', () => {
        // exercise
        var d = new Date(2014, 6, 15, 12, 0);
        var date = new rusk.model.FixdIntervalDate(d);
        
        // verify
        expect(date.toString()).toBe('2014/07/15 12:00');
    });
    
    it('分が 30 より大きく 60 より小さい場合、次の時間に切り上げられること', () => {
        // exercise
        var d = new Date(2014, 6, 15, 12, 31);
        var date = new rusk.model.FixdIntervalDate(d);
        
        // verify
        expect(date.toString()).toBe('2014/07/15 13:00');
    });
    
    it('分が 30 の場合、切り上げは行われないこと', () => {
        // exercise
        var d = new Date(2014, 6, 15, 12, 30);
        var date = new rusk.model.FixdIntervalDate(d);
        
        // verify
        expect(date.toString()).toBe('2014/07/15 12:30');
    });
    
    it('年月日だけの文字列形式が取得できること', () => {
        // exercise
        var d = new Date(2014, 6, 15, 12, 16);
        var date = new rusk.model.FixdIntervalDate(d);
        
        // verify
        expect(date.getDateAsString()).toBe('2014/07/15');
    });
    
    it('時分だけの文字列形式が取得できること', () => {
        // exercise
        var d = new Date(2014, 6, 15, 12, 16);
        var date = new rusk.model.FixdIntervalDate(d);
        
        // verify
        expect(date.getTimeAsString()).toBe('12:30');
    });
    
});