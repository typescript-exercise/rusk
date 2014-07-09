Rusk
====

さくさくタスクを片す Rusk

さくさくタスクが片付けられるようなタスク管理ツールができたらいいな、という願望から Rusk と名づけました。


##開発
###あらかじめインストールしておく必要のあるツール等
- Node.js（npm）
	- bower
	- tsd
	- grunt-cli
- Java (JDK8+)
- eclipse
	- TypeScript プラグイン

###プロジェクトのセットアップ
- プロジェクトをローカルに落とす（git clone or zip ダウンロード）
- コマンドラインを開き、プロジェクトのトップに移動する。
- 以下のコマンドを実行する

```sh
> gradlew

> npm install

> bower install

> tsd reinstall

> grunt init
```

###開発用コマンド
####プログラムを起動する
```sh
> gradlew run
```

Web ブラウザを立ち上げ、コマンドラインに表示されている URL にアクセスする。

####war ファイルを生成する
```sh
> gradlew war
```

`build/libs` の下に war ファイルが生成されます。

