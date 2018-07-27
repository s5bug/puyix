[![Codacy grade](https://img.shields.io/codacy/grade/8684d8355adf482780b32830c1491839.svg)](https://www.codacy.com/app/srn/puyix?utm_source=gitlab.com&amp;utm_medium=referral&amp;utm_content=srnb/puyix&amp;utm_campaign=Badge_Grade)
[![Codacy coverage](https://img.shields.io/codacy/coverage/8684d8355adf482780b32830c1491839.svg)](https://www.codacy.com/app/srn/puyix?utm_source=gitlab.com&utm_medium=referral&utm_content=srnb/puyix&utm_campaign=Badge_Coverage)
[![Pipeline status](https://gitlab.com/srnb/puyix/badges/master/pipeline.svg)](https://gitlab.com/srnb/puyix/commits/master)

# puyix

Puyix is built to be an extensible engine for games like Puyo Puyo Tetris. A main goal is
for each client or server to to be able to have their own set of plugins or gamemodes, and
for those modes to be able to compete fairly against one another.

## Why Monix and Cats and not Akka?

This project is meant to be a project that teaches me Monix and Cats while also teaching
me to keep my code clean. While Akka would be an appropriate fit for this project I
would not learn anything and, in my opinion, could degrade the quality and ease of
testing in my code.
