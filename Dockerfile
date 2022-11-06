FROM ubuntu:22.04

RUN bash << EOF
	# execute install script for terminal environment
	cd
	git clone https://github.com/Roni1993/.dotfiles
	cd .dotfiles
	./install

	# open zsh to install all zsh plugins
	chsh -s /usr/bin/zsh
	zsh
	zi update
EOF