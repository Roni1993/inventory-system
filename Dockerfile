FROM ubuntu:22.04

RUN <<EOF

cd
git clone https://github.com/Roni1993/.dotfiles
cd .dotfiles
./install

chsh -s /usr/bin/zsh
zsh
zi update

EOF