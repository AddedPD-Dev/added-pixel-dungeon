/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.effects.BadgeBanner;
import com.shatteredpixel.shatteredpixeldungeon.items.Honeypot;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.UnstableSpellbook;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Blandfruit;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gauntlet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Shuriken;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Tomahawk;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.Dart;
import com.shatteredpixel.shatteredpixeldungeon.messages.Languages;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_7_X_Changes {
	
	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		add_v_added_0_1_0_Changes(changeInfos);
		add_v0_7_5_Changes(changeInfos);
		add_v0_7_4_Changes(changeInfos);
		add_v0_7_3_Changes(changeInfos);
		add_v0_7_2_Changes(changeInfos);
		add_v0_7_1_Changes(changeInfos);
		add_v0_7_0_Changes(changeInfos);
	}

	public static void add_v_added_0_1_0_Changes( ArrayList<ChangeInfo> changeInfos ){
			// default : korean
			ChangeInfo changes = new ChangeInfo("AddedPD_v0.1.0", true, "");
			changes.hardlight(0x78DFFF);
			changeInfos.add(changes);

		if (SPDSettings.language() == Languages.KOREAN) {

			changes = new ChangeInfo("2020-03-19", false, null);
			changes.hardlight( Window.TITLE_COLOR );
			changeInfos.add(changes);

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.STAKE, null), "말뚝",
					"이제 사제와 드워프 생존자는 각각 _마법 보관집_과 _벨벳 주머니_를 가지고 시작합니다.\n\n" +
							"또한 사제는 고유한 투척 무기로 _말뚝_ 3개를 가지고 시작합니다. 지상과 비교적 가까운 덕분에, 하수구에선 말뚝의 내구도가 소모되지 않습니다. 그리고 다트처럼 쇠뇌에 장전할 수 있습니다!"));

			changes.addButton(new ChangeButton(new Image(Assets.CLERIC, 0, 15, 12, 15), "사제 상향",
					"_열광의 기도_가 더 많은 열광을 제공합니다."));

			changes.addButton(new ChangeButton(Icons.get(Icons.CLERIC), "대속자 상향",
					"대속자가 세례받은 대상을 _안식_에 들게 하면 _60_의 헌신을 돌려받습니다.\n\n +" +
							"또한, 세례받은 대상은 즉시 12의 추가 체력을 얻으며, 3레벨만큼 성장하면 세 가지 _특성_ 중 하나를 얻습니다!:\n" +
							"_- 신성한 활력_은 빠른 체력 재생력을 제공합니다. 죽어가는 상태에선 더욱 빠르게 재생합니다.\n" +
							"_- 원소 저항력_은 (원소의 반지처럼) 각종 해로운 효과와 적대적인 마법에 저항합니다.\n" +
							"_- 수호자의 영혼_은 주기적으로 자신을 제외한 주변 아군에게 방어막을 부여합니다."));

			changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "버그 수정"),
					"여태까지 보고된 각종 버그들이 수정되었습니다... 제대로 수정되었는지 확인해주세요!\n" +
							"_-_ 사제가 강타 기적을 사용한 후 헌신을 새로 얻기 전까지 기적을 쓸 수 없는 버그\n" +
							"_-_ 사제로 폐광에 첫 진입하면 게임이 깨지는 버그\n" +
							"_-_ 대속자가 세례 기적을 사용할 수 없는 버그\n" +
							"_-_ 드워프 생존자가 아닌 영웅으로 완력의 반지 사용시 게임이 깨지는 버그\n" +
							"_-_ 23층에 진입하면 게임이 깨지는 버그\n" +
							"_-_ 사제가 지팡이 깎는 노인의 퀘스트를 완료할 수 없는 버그"));

			changes = new ChangeInfo("사제", false, null);
			changes.hardlight(0xFFFFFF);
			changeInfos.add(changes);

			changes.addButton(new ChangeButton(new Image(Assets.CLERIC, 0, 15, 12, 15), "사제",
					"사제는 신성한 기적을 다룹니다. 이 컨셉은 고전적인 '신성 마법사' 캐릭터들과 00-Evan의 블로그 포스트로부터 영감을 얻었습니다. 사제를 해금하려면 한 게임을 지상으로 돌아감으로서 끝내야 합니다.\n\n" +
							"사제는 _곤봉_을 갖고 시작합니다. 곤봉은 _명중률이 높은_ 무기입니다. 원거리 무기로는 _말뚝_을 갖고 시작합니다. 말뚝은 5층까지는 내구도를 잃지 않고, 다트처럼 쇠뇌에 장전할 수 있습니다!\n\n" +
							"또한 사제 마법 보관집을 갖고 시작합니다. 보관집에는 원거리 무기를 넣어둘 수 있으며, 더 오래 쓸 수 있게 해줍니다.\n\n" +
							"사제는 _마법 막대와 주문 결정_을 거부하기로 맹세했습니다. 지팡이 깎는 노인의 보상도 경험의 물약 하나로 대체됩니다.\n\n" +
							"대신, 사제는 _빛의 신앙_에 헌신하여 어둠으로 가득한 던전에 빛의 힘을 불러낼 수 있습니다!"));

			changes.addButton(new ChangeButton(Icons.get(Icons.CLERIC), "빛의 신앙",
					"사제는 '헌신'을 소모하여 '기적'을 일으킵니다. 헌신은 적을 쓰러뜨려 경험치를 얻거나 레벨업할 때 얻게 됩니다. 사제는 세 종류의 기적을 배운 채로 시작합니다:\n\n" +
							"_강타_ - 헌신 15 소모\n" +
							"시야 안의 적 하나에게 저항할 수 없는 피해를 줍니다. 피해량은 사제의 레벨에 비례하며, 사악한 적에게 더 큰 피해를 줍니다.\n\n" +
							"_열광의 기도_ - 헌신 30 소모\n" +
							"사제와 아군의 체력을 재생합니다. 재생의 효과는 사제가 얼마나 위기에 처했는지에 따르므로, 쉴틈없는 정화야말로 답입니다!\n\n" +
							"_축성_ - 헌신 45 소모\n" +
							"무기가 갑옷 하나를 골라 빛의 힘을 주입합니다. 모든 저주와 마법 부여를 제거하고 고유한 효과를 부여합니다. 이는 돌이킬 수 없으며, 축성된 물건은 영웅의 유해로부터 얻을 수 없습니다.\n\n" +
							"세부 전직을 고르면, 일부 기적이 강화되거나 새로운 기적을 배웁니다."));

			changes.addButton(new ChangeButton(new Image(Assets.CLERIC, 0, 90, 12, 15), "세부 전직",
					"_성전사_ 는 사악한 마법과 맞서 싸움으로써 헌신합니다. 축성된 무기와 강타가 마법을 쓰는 적을 실명시키며, 열광의 기도가 신비한 갑옷 효과와 아드레날린을 제공합니다.\n\n" +
							"_신학자_는 빛의 원리를 배워나감으로써 헌신합니다. 헌신을 더욱 빠르게 얻고, 강타한 대상 주변에도 피해를 줍니다. 또한 '홍수의 은총', '화장터'를 새로운 기적으로 배우게 됩니다.\n\n" +
							"_대속자_는 저 길잃은 것들 중에서 회개할 가능성이 있는 이들을 도우며 헌신합니다. 아군에게 강타로 치유 효과를 줍니다. 또한 '세례'로 적을 아군으로 교화하여, 함께 경험을 쌓을 수 있습니다."));

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_CLERIC, new ItemSprite.Glowing(0xFFFFFF)), "전설 갑옷",
					"사제의 전설 갑옷은 '빛의 대변자' 입니다.\n\n" +
							"빛의 대변자는 _광휘_ 능력을 사용할 수 있습니다. 광휘는 사제의 시야 안의 모든 적을 실명시키고 밀쳐냅니다!"));

			changes = new ChangeInfo("드워프 생존자", false, null);
			changes.hardlight(0xFFC65A);
			changeInfos.add(changes);

			changes.addButton(new ChangeButton(new Image(Assets.DWARFHERO, 0, 15, 12, 15), "드워프 생존자",
					"온전한 정신을 지닌 마지막 드워프. 기계화된 정예 군인입니다. 악마들과의 옛 전쟁으로부터 살아남은 참전용사로, 드워프 왕의 폭정으로부터 가까스로 탈출했습니다. 드워프 생존자를 해금하려면 그를 찾아내어 구출해내야 합니다...\n\n" +
							"드워프 생존자는 _기계화된 팔_을 무기로 사용합니다. 그의 팔은 _빠른 공격속도_와 _추가 방어력_을 지녔지만 다른 무기를 집어들 순 없습니다. 기계화된 팔을 강화하면 추가 방어력도 증가합니다.\n\n" +
							"드워프 생존자는 원거리 무기 대신 5개의 _충격의 돌_을 갖고 시작합니다. 또한, 그는 (충격의 돌을 포함하여) 모든 종류의 전기 충격으로부터 잠시동안 _가속_되며, 전기 충격으로 인한 마비로부터 면역입니다.\n\n" +
							"드워프 생존자는 벨벳 주머니를 갖고 시작합니다."));

			changes.addButton(new ChangeButton(new Image(Assets.DWARFHERO, 0, 90, 12, 15), "세부 전직",
					"_천둥기수_는 드워프 군대의 정예병으로, 기계화된 팔을 개량하여 공격할 떄마다 강력한 연쇄 번개를 충전합니다. 충전이 4번째 되는 순간, 주변에 있는 적의 수만큼 연쇄 번개가 뻗어나가며 잠깐동안 가속 효과를 받습니다.\n\n" +
							"_수도승_은 서서히 집중을 쌓습니다. 집중은 이동할 때 더 빠르게 쌓이며, 집중을 발동하면 잠시동안 적의 공격을 완전히 회피합니다. 집중으로 회피에 성공하면 빈틈을 노리고 반격하여 적을 기절시킬 수 있습니다.\n\n" +
							"_사령장인_은 적을 쓰러뜨려 경험치를 얻을 때, 적으로부터 영혼 잔여물을 추출하여 사령공학 골렘을 작동시키고 수리합니다. 사령공학 골렘은 당신 주변에서만 활동 가능하며, 수호자 형상과 감시자 형상을 전환할 수 있습니다."));

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_DWARF, new ItemSprite.Glowing(0xFFC65A)), "전설 갑옷",
					"드워프 생존자의 전설 갑옷은 _드워프제 동력 갑옷_입니다. 경이로운 드워프 기술로 만들어져 마치 착용형 골렘같은 외형이죠.\n\n" +
							"드워프제 동력 갑옷은 _동력 과열_ 능력을 사용할 수 있습니다. 동력이 과열된 상태에선 모든 행동이 2배로 빨라집니다!"));

			changes = new ChangeInfo("3번째 세부 전직", false, null);
			changes.hardlight(Window.TITLE_COLOR);
			changeInfos.add(changes);

			changes.addButton(new ChangeButton(new Image(Assets.WARRIOR, 0, 90, 12, 15), "인장기사",
					"_인장기사_는 부서진 인장의 힘을 일깨웠습니다! 부서진 인장이 방어막을 2배 빠르게 재생하고, 갑옷의 상형문자까지 옮길 수 있습니다.\n\n" +
							"또한 인장이 붙은 갑옷의 상형문자가 강화로 지워지지 않도록 보존 가능하며, 종류에 따라 특수한 효과를 제공합니다:\n\n" +
							"_애정_ - 매혹된 적 공격시 방어막 회복\n" +
							"_항마_ - 흡수된 마법 피해로 반격 가능\n" +
							"_유황불_ - 빙결과 얼어붙음에도 면역\n" +
							"_위장_ - 기습 성공시 방어막 회복\n" +
							"_속박_ - 발동시킨 적 마비\n" +
							"_흐름_ - 물 위에서 공격속도 +20%\n" +
							"_흐릿함_ - 연막 면역과 함께 연막 생성\n" +
							"_전위_ - 받은 피해만큼 적의 방어, 회피 관통\n" +
							"_반발_ - 밀쳐낼 때마다 지구력 효과\n" +
							"_바위_ - 피격시 나무피부 효과\n" +
							"_신속함_ - 인접한 적 피격시 지구력 효과\n" +
							"_가시_ - 출혈된 적 공격시 방어막 회복\n" +
							"_점성_ - 공격 성공시 지연된 피해 감소"));

			changes.addButton(new ChangeButton(new Image(Assets.MAGE, 0, 90, 12, 15), "변환술사",
					"_변환술사_는 지팡이에 혼돈의 힘을 주입합니다. 충전량이 모두 소진된 지팡이를 시전하면, 충전량 하나와 함께 잠시동안 충전 효과를 받으며 지팡이의 마법이 변환됩니다!\n\n" +
							"그러나 지팡이의 강화 수치가 높을수록, 혼돈의 힘이 잠시 잔류하며 지팡이의 마법을 뒤틀어놓습니다. 또한 저주받은 지팡이는 이미 너무 많은 혼돈의 힘이 있기에 변환시킬 수 없습니다."));

			changes.addButton(new ChangeButton(new Image(Assets.ROGUE, 0, 90, 12, 15), "밤손님",
					"_밤손님_은 도둑질의 달인입니다. 닫혀있건 잠겨있건 문 너머를 바라볼 수 있고, 적을 쓰러뜨릴 때마다 주변의 지형으로부터 비밀을 밝혀냅니다.\n\n" +
							"만약 밤손님이 함정을 밟게 되면, 함정이 발동하기 직전에 초인적인 반사신경으로 해체해버립니다! 이 '반사신경'을 다시 발휘하려면 20턴의 대기 시간이 필요합니다."));

			changes.addButton(new ChangeButton(new Image(Assets.HUNTRESS, 0, 90, 12, 15), "영혼길잡이",
					"_영혼길잡이_는 사냥꾼의 적 감지 능력과 전설 갑옷의 유령 칼날로부터 영감을 얻어 만들어졌습니다. 영적인 감각을 단련함으로써, 2배로 넓어진 범위에서 적을 감지하게 됩니다.\n\n" +
							"영혼의 활로 공격에 성공하면, 대상으로부터 '유령 폭발'을 일으키게 됩니다. 이 폭발은 동일한 피해와 마법 부여 효과를 일으키며, 오직 적에게만 영향을 줍니다."));

			changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
			changes.hardlight(CharSprite.POSITIVE);
			changeInfos.add(changes);

			changes.addButton(new ChangeButton(new Image(Assets.WARRIOR, 0, 90, 12, 15), "검투사와 광전사",
					"_검투사_의 가르기로 유지 가능한 연속 타격의 시간이 12턴에서 20턴으로 증가했습니다. 연속 타격 수치가 초기화되는 시간도 4턴에서 6턴으로 증가했습니다.\n" +
							"_-_ 후려치기를 제외한 필살기는 사실 없는 거나 마찬가지였습니다. 적을 떼거지로 만나지 않는 이상 4 이상의 연속 타격 수치를 만드는 것부터가 굉장히 까다롭고, 제대로 쓰려면 짜증나기까지 하니까요! 그러나 이제 이를 좀 더 자주, 무엇보다 실용적으로 사용할 수 있을 겁니다.\n\n" +
							"_광전사_ 는 분노에 비례하여 '피해 저항'을 얻게 됩니다. 최대 100% 분노에서 받는 모든 피해의 20%를 줄여줍니다.\n" +
							"_-_ 우리 모두 광전사가 분노를 유지하는 일이 극단적으로 어렵다는 걸 알고 있습니다! 이것이 그동안 '전사에겐 세부 전직이 없어요' 라는 농담이 나온 이유죠. 그러나 추가 피해 저항은 낮은 체력을 유지하기로 선택할 때, 그런 선택을 할만큼 충분한 이점으로 작용할 겁니다."));

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_MAGE), "마법사의 로브",
					"마법사의 전설 갑옷 능력은 이제 마법사의 세부 전직에 따라 추가 효과를 일으킵니다.\n" +
							"_-_ _전투 마법사_는 대지 융해로 영향을 준 적의 수만큼 즉시 충전량을 얻습니다.\n" +
							"_-_ _흑마법사_는 대지 융해로 영향을 준 모든 적에게 영혼의 낙인을 찍습니다."));

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_TRANSFUSION), "수혈의 마법 막대",
					"이식의 마법 막대가 보다 원래 뜻에 가까운 '수혈의 마법 막대' 로 번역이 변경되었습니다.\n\n" +
							"전투 마법사가 수혈의 마법 막대를 융합한 지팡이로 공격하면, 이전보다 높은 확률로 '공짜 수혈' 효과를 얻습니다. 이는 버프창에 '생명력 충전됨' 버프로 표시됩니다."));

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_ROSE1), "슬픈 유령",
					"이제 장미로부터 불러낸 슬픈 유령과 시야를 공유합니다."));
		}

		else { // for ENG

			changes = new ChangeInfo("2020-03-19", false, null);
			changes.hardlight( Window.TITLE_COLOR );
			changeInfos.add(changes);

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.STAKE, null), "Stake",
					"Now the cleric and dwarf survivor start with _magical holster_ and _velvet pouch_.\n\n" +
							"Also, the cleric starts with 3 _ Stakes_ as her unique throwing weapon. Because the surface is still near, using the stake ar the sewer isn't reduce durability. And stake can be loaded into the crossbow, like a dart!"));

			changes.addButton(new ChangeButton(new Image(Assets.CLERIC, 0, 15, 12, 15), "Cleric Buff",
					"_Pray of Zealot_ now grants more zeal."));

			changes.addButton(new ChangeButton(Icons.get(Icons.CLERIC), "Redeemer Buff",
					"When the redeemer prays the rest of peace for her baptized ally, she restore _60_ devotion.\n\n +" +
							"Also, the baptized ally gets +12 bonus HP immediately, and gets new perk when it reaches 3rd level! :\n" +
							"_- Divine Vigor_ grants faster regeneration, more faster when injured.\n" +
							"_- Elemental Resistance_ allow to resist from harmful effects and hostile magic.\n" +
							"_- Guardian Spirit_ sometimes emits shielding aura to nearby allies, expect itself."));

			changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "Bugfix"),
					"Various bugs reported so far have been fixed ... Please make sure they are correct!"));

			changes = new ChangeInfo("The Cleric", false, null);
			changes.hardlight(0xFFFFFF);
			changeInfos.add(changes);

			changes.addButton(new ChangeButton(new Image(Assets.CLERIC, 0, 15, 12, 15), "The Cleric",
					"The Cleric is the 'Divine Caster', concepts from old-classic divine caster class and inspired by the DeVBlog of 00-Evan. Unlocked when you ends a run return to the surface.\n\n" +
							"The Cleric starts with the _cudgel_, which only had its name in the source. The cudgel is _accurate_ weapon. And the three _stake_ are also cleric's starting throwing weapon, which can be used instead of darts when firing crossbow.\n\n"+
							"Also she starts with the _magical holster_, which bag can contains ranged weapons and allows to collect easily them.\n\n" +
							"She had an oath to _reject magic from wands and spell crystals._ The Wandmaker's reward also changes into a potion of experience.\n\n" +
							"Instead, she devoted to _the faith of the light_, and call down the power of the brightness into the dungeon!"));

			changes.addButton(new ChangeButton(Icons.get(Icons.CLERIC), "The faith of the light",
					"The Cleric build up 'devotion' rank, which spends using 'miracle'. The devotion rank increases when defeats enemy with gain experience or level up. The Cleric starts with three miracles:\n\n" +
							"_Smite_ - costs 15 devotion\n" +
							"Selects an enemy in cleric's sight, deals irresistible damage. Damage depending on cleric's level, and deals more damage to evil enemies.\n\n" +
							"_Pray of zealot_ - costs 30 devotion\n" +
							"Gain regeneration buff to cleric and allies. The amount of regeneration depending on the danger of the situation. If you wants to regen health, restless purge is an answer!\n\n" +
							"_Enlightenment_ - costs 45 devotion\n" +
							"Choose an weapon/armor for imbued by light. Removes all kinds of curse and enchantment/glyph and gives unique effects. Enlightened items can't revert and never drops from Hero's remain.\n\n" +
							"When you choose subclass, some miracles will change or learn new miracles."));

			changes.addButton(new ChangeButton(new Image(Assets.CLERIC, 0, 90, 12, 15), "The Cleric subclass",
					"_The Crusader_ devotes by fighting against dark magic. Enlightened weapon or smite will blinds magic-using enemies. Also the Pray of zealot gains arcane armor and adrenaline.\n\n" +
							"_The Scholar_ devotes by learning about the principle of light. The devotion gains faster, and the smite deals AoE damage. Also adds 'Holy Flood', 'Crematorium' miracle on the miracles list.\n\n" +
							"_The Redeemer_ devotes by saving some of those stray beings can be reclaimed. Smite ally will gain healing buff. Also Adds 'Baptize' miracle to reclaim an enemy, which is able to level up."));

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_CLERIC, new ItemSprite.Glowing(0xFFFFFF)), "Epic armor",
					"The Cleirc's epic armor is named 'Light's Advoate'.\n\n" +
							"The Light's Advocate has an ability called _'Emit Radiance'_: push all enemies in your sight and blind them shortly."));

			changes = new ChangeInfo("The Dwarf Survivor", false, null);
			changes.hardlight(0xFFC65A);
			changeInfos.add(changes);

			changes.addButton(new ChangeButton(new Image(Assets.DWARFHERO, 0, 15, 12, 15), "The Dwarf Survivor",
					"The Last Dwarf Standing. Mechanized veteran soldier, survived from the old war against the demons and tyranny of the dwarf king. Unlocked when you rescue him from the hidden place...\n\n" +
							"The Dwarf Survivor use his _mechanized arms_ as weapon. His arms are _fast_ and _durable_ weapon, but cannot wield any other weapon. Upgrade them will _increase those durability_.\n\n" +
							"Instead of ranged weapon, the Dwarf Survivor starts with five _stone of shock_. And including stone of shock, he is _hastened_ for a while from all kinds of electric shocks. It means he is fully immune to electro-paralyze.\n\n" +
							"The Dwarf Survivor starts with Velvet pouch."));

			changes.addButton(new ChangeButton(new Image(Assets.DWARFHERO, 0, 90, 12, 15), "The Dwarf subclass",
					"_The Thunderbrigner_ reforged his arms, allow to blast chain lightning when thunder charges reach at 4. Successful attack charges thunder, and blast lighting will hasten you.\n\n" +
							"_The Monk_ builds the 'focus' over time, builds faster while moving. The focus allow him to dodge all attacks, and to perform paralyzing blow after focus-dodging.\n\n" +
							"_The Necrosmith_ extracts ectoplasm from worthy enemies. These souls are consumed as a fuel for the necroforged golem. His golem can be healed by consume ectoplasm, and transform between guaridan or sentry form."));

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_DWARF, new ItemSprite.Glowing(0xFFC65A)), "Epic armor",
					"The Dwarf Survivor's epic armor is 'Dwarven Power Armor'. Made by wondrous dwarven technology, this armor looks like mobile golem.\n\n" +
							"The Dwarven Power Armor has an ability to _'Overheat'_: All actions are twice as fast in a short time."));

			changes = new ChangeInfo("3rd subcalsses", false, null);
			changes.hardlight(Window.TITLE_COLOR);
			changeInfos.add(changes);

			changes.addButton(new ChangeButton(new Image(Assets.WARRIOR, 0, 90, 12, 15), "The Sealknight",
					"_The Sealknight_ awakes power of the broken seal! His seal regenerates shields as twice as faster, and can also transfer glyph.\n\n" +
							"Also, the glyph on the seal-affixed armor can be preserved from upgrade scrolls(or choose erase glyph), and gain additional effects:\n\n" +
							"_affection_ - gain shield by hit charmed enemy\n" +
							"_antimagic_ - counter-zap by absorbed damage\n" +
							"_brimstone_ - also immune to chill and frost\n" +
							"_camouflage_ - gain shield by surprise attack\n" +
							"_entanglement_ - stun enemy which activates armor\n" +
							"_flow_ - +20% faster attack on the water\n" +
							"_obfuscation_ - emits and immune smoke screen\n" +
							"_potential_ - infuse attack by damage taken to bypass armor\n" +
							"_repulsion_ - gain stamina when pushing enemy\n" +
							"_stoen_ - gain barkskin when attacked\n" +
							"_swift_ - gain stamina when attacked nearby enemy\n" +
							"_thorns_ - gain shield by hit bleeding enemy\n" +
							"_viscosity_ - reduce deferred damage by hit enemy"));

			changes.addButton(new ChangeButton(new Image(Assets.MAGE, 0, 90, 12, 15), "Transmuter",
					"_The Transmuter_ infuse his staff with chaotic energy. Zapping the empty charged staff, transmuter gain 1 charge and recharging buff, and transmute the magic of staff!\n\n" +
							"But depend on the upgrades, the more chaotic energy lasts to distort maigc. Also the cursed staff can't transmute because its already filled by chaotic energy."));

			changes.addButton(new ChangeButton(new Image(Assets.ROGUE, 0, 90, 12, 15), "The Burglar",
					"_The Burglar_ is master of thievery. He can see through doors(even locked), and detects area from defeated enemy.\n\n" +
							"When the burglar steps on a trap, reflexively disarms just before it activates! This reflex needs 20 turns for cooldown."));

			changes.addButton(new ChangeButton(new Image(Assets.HUNTRESS, 0, 90, 12, 15), "The Spiritwalker",
					"_The Spiritwalker_, inspired by detect perk and spectral blades. She trained her spiritual sense, now detects enemies from wider area.\n\n" +
							"The spirit bow will cause 'spectral burst' around your target. This explosion has the equal damage and enchantments, and only deals damage to enemy."));

			changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
			changes.hardlight(CharSprite.POSITIVE);
			changeInfos.add(changes);

			changes.addButton(new ChangeButton(new Image(Assets.WARRIOR, 0, 90, 12, 15), "The Gladiator and the Berseker",
					"_The Gladiator_'s Cleave finisher extends combo by 20 turn (from 12 turn), and time to reset combo extends by 6 turn (from 4 turn).\n" +
							"_-_ Except Clobber, many finishers require more than 4 combo, were virtually absent. Because unless you've encountered hoard, you won't be able to maintain combo. But now you can use them more often, more practically.\n\n" +
							"_The Berserker_ has bonus 'damage resistance' from rage, maximum at 100% rage with reduce all damage taken by 20%.\n" +
							"_-_ Everyone knows his rage is extremely unmaintainable! This is why the joke that 'warrior has no subclass' came out. Bonus DR is worthy boon when you choose to take a risk for maintain rage."));

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_MAGE), "Mage robe",
					"The Mage's epic armor has special effects by subclass.\n" +
							"_-_ _The Battlemage_ recharges immediately depends on the number of enemies affected by Molten Earth.\n" +
							"_-_ _The Warlock_ marks souls of unmarked enemies affected by Molten Earth for a short time."));

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_TRANSFUSION), "Wand of transfusion",
					"When the battle mage attacks with his staff that fuses blood transfusion, it has a higher chance of getting a 'free transfusion' effect.\n" +
							"This is indicated by a 'Health Charged' buff in the buff window."));

			changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_ROSE1), "Sad Ghost",
					"You can now share vision with the sad ghost."));
		}

	}
	
	public static void add_v0_7_5_Changes( ArrayList<ChangeInfo> changeInfos ){
		ChangeInfo changes = new ChangeInfo("v0.7.5", true, "");
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes = new ChangeInfo("", false, null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes = new ChangeInfo("v0.7.5e", false, null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new Image(Assets.SNAKE, 12, 0, 12, 11), "Snake adjustments",
				"Snakes are doing a good job of filling their role as an enemy that demands being surprise attacked, but they are a bit annoying if the player gets unlucky and has to surprise them multiple times.\n\n" +
				"I'm tweaking them so that they are much more likely to die from a single surprise hit, but their danger otherwise should be very similar:\n" +
				"_-_ Snake health reduced to 4 from 6\n" +
				"_-_ Snake evasion increased by 25%\n\n" +
				"Snakes now also have an item drop! They will occasionally drop a random seed."));
		
		changes.addButton( new ChangeButton(new Image(Assets.TENGU, 0, 0, 14, 16), "Tengu Adjustments",
				"Tengu is in a much better place balance-wise since the changes in 0.7.5b, but he's still ruining the rogue's day a bit too often.\n\n" +
				"I'm buffing invisibility versus Tengu again, so that it completely avoids his regular attacks, but doesn't totally trivialize him:\n" +
				"_-_ Tengu now cannot attack invisible heroes\n" +
				"_-_ Tengu can now use his 3rd phase abilities against heroes he cannot see\n" +
				"_-_ VFX for Tengu's abilities now triggers even if the player can't see them"));
		
		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed (existed prior to 0.7.5):\n" +
				"_-_ Small amounts of stuttering when the hero moves\n" +
				"_-_ Rare layout issues with buttons in item windows\n" +
				"_-_ Bolts from wand of lightning not spreading in many cases where they should\n" +
				"_-_ Various rare crash bugs"));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.LANGS), Messages.get(ChangesScene.class, "language"),
				"Updated Translations"));
		
		changes = new ChangeInfo("v0.7.5c&d", false, null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"_-_ Made additional tweaks to camera movement speed when following hero, should be slightly faster in most cases."));
		
		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed (caused by 0.7.5):\n" +
				"_-_ Various visual bugs on floor 10\n" +
				"_-_ Text being highlighted when it shouldn't in specific cases\n" +
				"_-_ Letters failing to render in various specific cases\n" +
				"_-_ Camera moving slower than intended when zoomed in\n" +
				"_-_ Camera jittering at low framerates\n" +
				"_-_ Various rare crash bugs\n\n" +
				"Fixed (existed prior to 0.7.5):\n" +
				"_-_ Thrown weapons sticking to corrupted characters when they shouldn't"));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.LANGS), Messages.get(ChangesScene.class, "language"),
				"Updated Translations"));
		
		changes = new ChangeInfo("v0.7.5a&b", false, null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.LIBGDX), "LibGDX Text Rendering!",
				"The game's text renderer is now using LibGDX freetype. This looks almost identical to the existing text but is slightly crisper, platform-independent, and much more efficient!\n\n" +
				"Text rendering was the last bit of android-dependant code, so the game's core code modules (~98% of its code) are now being compiled as general code and not android-specific code!\n\n" +
				"Also updated translations"));
		
		changes.addButton( new ChangeButton(new Image(Assets.TENGU, 0, 0, 14, 16), "Enemy Balance Adjustments",
				"Tengu has been adjusted to be a bit less difficult for melee characters, in particular for the rogue:\n" +
				"_-_ Tengu blink distance on phase 3 reduced by 1 tile\n" +
				"_-_ Tengu accuracy reduced by 10%\n" +
				"_-_ Tengu accuracy versus invisible characters reduced by 50%\n\n" +
				"Additionally, some minor balance changes have been made to regular enemies:\n" +
				"_-_ Snake damage down to 1-4 from 1-5\n" +
				"_-_ Crab damage down to 1-7 from 1-8\n" +
				"_-_ Slime damage down to 2-5 from 3-5\n" +
				"_-_ Necromancer Skeleton HP on summon up to 20/25 from 15/25"));
		
		changes.addButton( new ChangeButton(new WandOfCorruption(),
				"The nerfs to the wand of corruption in 0.7.5 had basically no effect on its winrate when upgraded, so I'm taking a different approach and buffing its base power but reducing its upgraded power. I'm also putting more emphasis on debuffs helping corruption chances:\n\n" +
				"_-_ Corruption resistance reduction from minor debuffs up to 25% from 12.5% (was 20% prior to 0.7.5)\n" +
				"_-_ Corruption resistance reduction from major debuffs up to 50% from 25% (was 33% prior to 0.7.5)\n" +
				"_-_ Corruption power adjusted to 3+lvl/2 from 2+lvl\n\n" +
				"_-_ Wraith corruption resistance reduced slightly, to put them into line with other uncommon enemies."));
		
		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed (caused by 0.7.5):\n" +
				"_-_ Necromancers incorrectly only summoning skeletons at melee range\n" +
				"_-_ Rare cases where doors would appear incorrectly on floor 5\n" +
				"_-_ Doors not opening when they should in some cases\n" +
				"_-_ Necromancers rarely healing skeletons after they die\n" +
				"_-_ Various rare crash bugs\n\n" +
				"Fixed (existed prior to 0.7.5):\n" +
				"_-_ Black texture errors on older android devices\n" +
				"_-_ Scenes not fading in when they should in certain cases"));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Developer Commentary",
				"_-_ Released October 2nd, 2019\n" +
				"_-_ 76 days after Shattered v0.7.4" +
				"\n" +
				"Dev commentary will be added here in the future."));
		
		changes.addButton( new ChangeButton(new Image(Assets.SNAKE, 12, 0, 12, 11), "Sewer Enemies",
				"Two new enemies have been added to the sewers!\n\n" +
				"_- Snakes_ are an evasive enemy which mainly shows up on early floors, they help teach the importance of surprise attacks.\n" +
				"_- Slimes_ primarily appear on floor 4, and are an enemy type which rewards defense over damage.\n\n" +
				"Goo's level has also received significant changes. It now uses a new unique level layout pattern, and Goo itself always spawns in a new unique room type.\n\n" +
				"I have also made slight balance changes to the Goo fight itself. 1x1 pillars have been mostly removed from Goo's arena to reduce surprise-attack spam, but Goo's damage has been reduced by 20% to compensate."));
		
		changes.addButton( new ChangeButton(new Image(Assets.TENGU, 0, 0, 14, 16), "Prison Enemies",
				"_Necromancers_ have been added to the prison! These powerful enemies fight by summoning and buffing undead.\n\n" +
				"The _Tengu_ boss fight has been totally reworked! The fight still takes place over 3 stages, and has a similar core theme, but I have totally ditched the tedious maze and chasing mechanics from stages 2&3, and have given Tengu several new abilities. Watch your step!\n\n" +
				"As a part of this rework, Tengu's stats have also been adjusted:\n" +
				"_-_ HP up to 160 from 120\n" +
				"_-_ Evasion reduced by 25%\n" +
				"_-_ Damage reduced by 30%"));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new Image(Assets.RAT, 0, 15, 16, 15), "Enemy Changes",
				"_-_ Significantly improved the consistency of enemy spawns (large numbers of the same enemy and large enemy groups should be less common)\n\n" +
				"_-_ Adjusted enemy spawn chances on floors 1-10 to make rooms for new enemies\n\n" +
				"_-_ Skeletons no longer rarely appear on floor 4\n\n" +
				"_-_ Guards no longer drop healing potions, they are now dropped by necromancers\n" +
				"_-_ Guards now grant 7 exp, up from 6\n\n" +
				"_-_ Albino rats now grant 2 exp, up from 1\n" +
				"_-_ Albino rats now drop mystery meat"));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"_-_ The game camera now smoothly follows the hero while they are moving, instead of snapping to their location.\n\n" +
				"_-_ Standardized word use when attacks miss to reduce confusion. Enemies now always 'block' or 'dodge'.\n\n" +
				"_-_ Various improvements to wording on the supporter menu for Google Play users.\n\n" +
				"_-_ Various internal code improvements"));
		
		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed:\n" +
				"_-_ Various stability issues caused by the LibGDX conversion\n" +
				"_-_ Area-based effects behaving oddly in rare cases\n" +
				"_-_ Thieves not escaping when they should in many cases\n" +
				"_-_ A rare crash bug involving boomerangs\n" +
				"_-_ Sai and gauntlets giving 1 more defense than what their descriptions stated\n" +
				"_-_ Players rarely opening containers/doors from a distance"));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.LANGS), Messages.get(ChangesScene.class, "language"),
				"Added new Language: Japanese!\n\n" +
				"Updated Translations and Translator Credits!"));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_BLAST_WAVE, null), "Wand Buffs",
				"Blast wave is an excellent sidearm wand, but not as good when invested in. I'm making the wand a bit stronger and less risky to hopefully make it more worthy of upgrades.\n" +
				"_-_ Increased AOE damage from 67% to 100%, AOE knockback force is unchanged\n" +
				"_-_ AOE no longer damages hero/allies, but still knocks them back\n\n" +
				"Corrosion is a very powerful wand in the right hands, but is currently a bit too hard to use right. I'm lightly buffing it to make its power a bit more accessible.\n" +
				"_-_ Corrosion gas starting damage increased to 2+lvl from 1+lvl"));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_AMETHYST, null), "Ring Buffs",
				"Based on their performance, I'm giving a light buff to ring of energy, and a more significant buff to ring of wealth:\n\n" +
				"_-_ Ring of energy charge boost increased to 30% per level, from 25%\n\n" +
				"_-_ Ring of wealth exclusive drops are 20% more common\n" +
				"_-_ Rare ring of wealth exclusive drops are now 33% more common"));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.LONGSWORD, new ItemSprite.Glowing( 0x440066 )), "Glyph/Enchant Buffs",
				"I'm giving some significant buffs to underperforming rare enchants/glyphs:\n\n" +
				"_-_ Proc chance for corruption enchant increased by ~25% at all levels\n\n" +
				"_-_ Proc chance for glyph of affection increased by ~50% at +0, scaling to ~10% at +10"));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new WandOfLivingEarth(),
				"I'm continuing to adjust the wand of living earth to make it less able to stand on its own as a run-winning item. It should excel at providing defensive power, but shouldn't also give good offense.\n\n" +
				"_-_ Guardian average damage decreased by 33%\n" +
				"_-_ Base wand damage up to 4-6 from 3-6\n" +
				"_-_ Wand damage scaling down to 0-2 from 1-2"));
		
		changes.addButton( new ChangeButton(new WandOfCorruption(),
				"Corruption is performing extremely well when invested in, so I'm adjusting debuff influence on corruption chance to make it more difficult to corrupt enemies.\n\n" +
				"_-_ Corruption resistance reduction from minor debuffs reduced to 12.5% from 20%\n" +
				"_-_ Corruption resistance reduction from major debuffs reduced to 25% from 33%"));
		
	}

	public static void add_v0_7_4_Changes( ArrayList<ChangeInfo> changeInfos ){
		ChangeInfo changes = new ChangeInfo("v0.7.4", true, "");
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Developer Commentary",
				"_-_ Released July 18th, 2019\n" +
				"_-_ 56 days after Shattered v0.7.3" +
				"\n" +
				"Dev commentary will be added here in the future."));

		changes.addButton( new ChangeButton(new WandOfWarding(),
				"This brand new wand spawns autonomous wards which attack enemies. Wards can be upgraded by being zapped again, and eventually form up into sentry turrets.\n\n" +
				"The Wand of Warding does very consistent damage, but requires some setup first."));

		changes.addButton( new ChangeButton(new WandOfLivingEarth(),
				"This new wand has a lower damage output, but grants significant defensive power. The rocks the wand shoots at enemies reform around the hero and absorb damage. If enough rock is built, it will form up into a rock guardian which fights with the player.\n\n" +
				"The Wand of Living Earth is lacking in offensive output, but does a great job of pulling focus and damage away from the player."));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.LIBGDX), "LibGDX",
				"Large sections of Shattered's codebase is now using the multiplatform game library _LibGDX._ Making the game's codebase less heavily tied to Android is a big step towards making the game available on other platforms!\n\n" +
				"Keyboard input handling and text rendering are still coupled to Android however. I will convert these game systems to use LibGDX in a later update.\n\n" +
				"Note that Shattered will not immediately release on other platforms once LibGDX conversion is complete, but it is a big step towards that."));

		changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.EXOTIC_BERKANAN), "Ally AI improvements",
				"Allies which follow the player are now considered to be 'intelligent', and have the following improved behaviours:\n" +
				"_-_ Intelligent allies will not attack enemies which are asleep, or which haven't noticed the player yet.\n" +
				"_-_ Intelligent allies will follow the hero through stairs so long as they are near to them.\n\n" +
				"Lastly, the hero can now swap places with any ally, even unintelligent ones."));

		changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"_-_ Overhauled main menu interface to allow for more expandability.\n" +
				"_-_ Updated hero icons in rankings and saved game screens.\n\n" +
				"_-_ Class armor abilities no longer affect allies\n" +
				"_-_ Autotargeting now no longer targets ally characters, in any circumstances.\n" +
				"_-_ Most scrolls with an area of affect now no longer affect allies. More destructive ones will still damage them though.\n" +
				"_-_ Added a little surprise if you reach the surface with an upgraded ally item.\n\n" +
				"_-_ Ring of elements and antimagic effects now apply to damage from wands.\n\n" +
				"_-_ The great crab can now only block one enemy at a time.\n\n" +
				"_-_ Shattered Pixel Dungeon now requires Android 2.3+ to run, up from Android 2.2+.\n" +
				"_-_ Google Play Games and sharing gameplay data now requires android 4.1+, up from 4.0+."));

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed:\n" +
				"_-_ Talisman of foresight warn effect not being saved/loaded\n" +
				"_-_ Level visuals (e.g. prison torches) rarely bugging out\n" +
				"_-_ Minor visual errors with ranged enemy attacks.\n" +
				"_-_ Heavy boomerangs being lost when inventory is full\n" +
				"_-_ NPCs rarely getting hit by ranged attacks\n" +
				"_-_ Enemies rarely spawning on top of each other on boss levels\n" +
				"_-_ Elixir of aquatic rejuvenation being able to heal slightly over max hp\n" +
				"_-_ Prismatic images not being affected by brimstone and antimagic glyphs\n" +
				"_-_ Shattered pots being lost if the player has a full inventory\n" +
				"_-_ Doors incorrectly closing when swapping places with an ally\n" +
				"_-_ Various rare bugs with heavy boomerangs\n" +
				"_-_ Various minor text errors"));

		changes.addButton( new ChangeButton(Icons.get(Icons.LANGS), Messages.get(ChangesScene.class, "language"),
				"Updated Translations"));

		changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(new DriedRose(),
				"The Dried Rose's ghost hero has received some buffs and adjustments to go along with other ally improvements:\n\n" +
				"_-_ The ghost hero can now be given instructions by using the rose after summoning them, and tapping on a location.\n\n" +
				"_-_ Ghost HP scaling increased to 8 per petal, from 4.\n" +
				"_-_ Ghost evasion reduced to 1x hero evasion from 2x.\n" +
				"_-_ Ghost now heals over time while they are summoned."));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.NOISEMAKER, null), "Enhanced Bomb Buffs",
				"Many enchant bombs are performing poorly compared to some of the more popular ones, such as holy bombs and boss bombs. While I am toning down the strongest bombs a bit, I'm also making some pretty significant buffs to weaker bombs:\n\n" +
				"_-_ Frost bomb cost down to 2 from 3, now instantly freezes enemies caught in the blast in addition to chilling.\n" +
				"_-_ Woolly bomb cost down to 2 from 3, now does regular bomb damage in addition to spawning sheep.\n" +
				"_-_ Noisemaker now explodes when an enemy is attracted to its location.\n" +
				"_-_ Flashbang cost increased to 6 from 5, now deals regular bomb damage and debuffs in a smaller AOE.\n" +
				"_-_ Shock bomb cost increased to 6 from 5, now stuns/damages immediately instead of over time with electricity.\n" +
				"_-_ Regrowth bomb cost increased to 8 from 6, now heals significantly more and spawns more plants."));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.LONGSWORD, new ItemSprite.Glowing(0xFF4400)), "Enchant/Glyph Buffs",
				"Continuing from the changes in 0.7.3, I'm still watching enchantment balance and making buffs where there's room to do so:\n\n" +
				"_-_ Blazing Enchantment bonus damage increased to 2/3 of burning damage, from 1-3.\n" +
				"_-_ Shocking Enchantment damage increased to 40% from 33%.\n" +
				"_-_ Blooming Enchantment chance for a second tile of grass increased to 10% per level, from 5%.\n" +
				"_-_ Lucky Enchantment proc chance scaling with levels increased by ~2x.\n" +
				"_-_ Corrupting Enchantment base proc chance increased to 15% from 10%, scaling reduced to compensate.\n\n" +
				"_-_ Glyph of Flow now grants a flat 2x speed boost in water, up from 1.5x + 0.1x per level."));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WILD_ENERGY, null), "Misc Item Buffs",
				"_-_ Wild energy now gives 4 turns of charging instantly, and 8 turns over time. Up from 10 turns over time.\n\n" +
				"_-_ Stone of Clairvoyance radius increased to 12 from 8. This increases the area by ~2.25x.\n\n" +
				"_-_ Allies are now healed by magical sleep, just like the hero."));

		changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton( new Image(Assets.MAGE, 0, 90, 12, 15), "Subclass Adjustments",
				"The Warlock is intended to require a source of physical damage in addition to a wand to be successful. Upgradeable ally wands are problematic for warlock as you can get magical power and physical damage in one item, which makes his abilities absurdly useful with them. The warlock should synergize with allies, but I have scaled the amount down to more reasonable levels:\n\n" +
				"_-_ Soul mark healing increased to 40% of damage from 33%\n" +
				"_-_ Soul mark is now 2/5 as effective when the damage-dealer isn't the hero.\n\n" +
				"I'm also making a few smaller adjustments to other subclasses which are overperforming:\n\n" +
				"_-_ Berserker rate of rage loss over time increased by 33% (it is now 2/3 of what it was pre-0.7.3).\n\n" +
				"_-_ Freerunner bonus evasion reduced by 20%."));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_MAIL, new ItemSprite.Glowing(0x88EEFF)), "Glyph Nerfs",
				"_-_ Glyph of Thorns fully charged amount reduced to 4+lvl from 4+2*lvl, proc rate increased.\n\n" +
				"_-_ Glyph of Antimagic base damage reduction reduced to 0-4 from 2-4.\n\n" +
				"_-_ Glyph of Brimstone shield generation removed. The glyph now only protects the user from fire and does not also grant shielding when the user is aflame."));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARCANE_BOMB, null), "Enhanced Bomb Nerfs",
				"_-_ Holy Bomb no longer blinds characters caught in the blast, recipe cost up to 8 from 6.\n\n" +
				"_-_ Arcane Bomb damage now falls off based on distance. Reduced to 100%/83%/67% from all 100%.\n\n" +
				"_-_ Shrapnel Bomb damage now slightly falls off based on distance. Damage is reduced by 5% per tile of distance."));

	}
	
	public static void add_v0_7_3_Changes( ArrayList<ChangeInfo> changeInfos ){
		ChangeInfo changes = new ChangeInfo("v0.7.3", true, "");
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Developer Commentary",
				"_-_ Released May 23rd, 2019\n" +
				"_-_ 66 days after Shattered v0.7.2" +
				"\n" +
				"Dev commentary will be added here in the future."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.LONGSWORD, new ItemSprite.Glowing(0xFFFF00)), "Enchantment Changes",
				"Several changes have been made to enchantments, based on feedback from 0.7.2:\n\n" +
				"_-_ Precise and swift enchantments have been removed.\n\n" +
				"_-_ Lucky and blooming are now uncommon enchants, instead of rare and common.\n\n" +
				"_-_ Kinetic is a new common enchantment! This enchantment preserves excess damage when an enemy is killed and applies it to your next hit.\n\n" +
				"_-_ Corrupting is a new rare enchantment! When killing an enemy, there is a chance you will corrupt it instead."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.KUNAI, null), "New Thrown Weapons",
				"Four new thrown weapons have been added!\n\n" +
				"_-_ Throwing clubs are a tier-2 weapon with extra durability\n\n" +
				"_-_ Kunai are a tier-3 weapon with bonus damage on sneak attacks\n\n" +
				"_-_ Heavy boomerangs are a tier-4 weapon which returns after being thrown\n\n" +
				"_-_ Force cubes are a tier-5 weapon which damage enemies in a 3x3 area"));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ELIXIR_ARCANE, null), "New Boss Recipes",
				"Two new recipes have been added, one which uses goo blobs and another which uses metal shards.\n\n" +
				"_-_ Elixir of arcane armor requires a goo blob and a potion of earthen armor. It grants a long-lasting resistance to magic.\n\n" +
				"_-_ Wild energy requires a metal shard and a scroll of mystical energy. It grants large amounts of recharging, but with some unpredictable effects attached!"));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new Dart(),
				"Dart tipping has been removed from the alchemy system. Darts can instead be tipped right from the inventory.\n\n" +
				"Tipped darts have had their shop price reduced by 33%, and can now be cleaned if you don't wish to use the effect.\n\n" +
				"The alchemy guide has been adjusted due to the removal of dart tipping from alchemy. It now has 9 pages (down from 10), and the order of pages have been adjusted to put some simpler recipes earlier."));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"_-_ Shattered honeypots are now stackable, and can be sold for a small amount of gold.\n\n" +
				"_-_ The changes list has been split into three separate groups, so that the game's entire change history isn't loaded all at once.\n\n" +
				"_-_ Tengu now throws his shurikens one at a time, just like other ranged enemies. The speed of the shurikens has been increased to compensate, so that the player doesn't need to keep waiting while Tengu's attacks are in flight.\n\n" +
				"_-_ After the tengu boss battle, any extra items now drop in tengu's cell, instead of a random prison cell.\n\n" +
				"_-_ The hero will no longer step onto visible traps if that trap wasn't discovered when movement started.\n\n" +
				"_-_ When the mage's staff is cursed, the wand within the staff will now also be cursed.\n\n" +
				"_-_ Scrolls of transmutation can now be used on thrown weapons.\n\n" +
				"_-_ Improved the coloration of crystal keys. They should now be more distinct from iron keys."));
		
		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed:\n" +
				"_-_ Prismatic images causing errors when falling into pits\n" +
				"_-_ Secret rooms never spawning in the earlier parts of a region\n" +
				"_-_ Curse of multiplicity not working correctly on boss floors\n" +
				"_-_ Curse of multiplicity closing doors when it shouldn't\n" +
				"_-_ Ring of wealth rarely generating items which are blocked by challenges\n" +
				"_-_ Windows rarely appearing in places they shouldn't\n" +
				"_-_ Odd behaviour when the player is killed by electricity or a grim weapon\n" +
				"_-_ Explosions destroying armor with the warrior's seal on it\n" +
				"_-_ Various minor visual bugs\n" +
				"_-_ Various rare crash bugs"));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.LANGS), Messages.get(ChangesScene.class, "language"),
				"Updated Translations"));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton( new Image(Assets.WARRIOR, 0, 90, 12, 15), "Berserker & Gladiator",
				"Because of nerfs I have made to the scaling of the warrior's shield regen, I have some power budget to give to his subclasses!\n\n" +
				"Berserker rate of rage loss decreased by 50%. It should now be easier to hold onto rage at higher health, but being injured will still help to retain it longer.\n\n" +
				"Gladiator is now significantly more flexible:\n" +
				"_-_ Using items no longer resets combo\n" +
				"_-_ Throwing weapons now increment combo\n" +
				"_-_ Slam ability now deals damage based on armor, instead of simply increasing damage."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.CURSE_INFUSE, null), "Boss Recipe Buffs",
				"All recipes made with ingredients dropped by bosses have been buffed (except bombs):\n\n" +
				"_-_ Caustic brew now affects a 7x7 area, up from 5x5. Energy cost of caustic brew reduced to 4 from 8.\n\n" +
				"_-_ Elixir of aquatic rejuvenation now heals faster, and does not waste healing if the hero is not in water. Total amount of healing reduced to compensate.\n\n" +
				"_-_ Curse Infusion now grants a single upgrade to wands/weapons/armor in addition to cursing. This upgrade is lost if the item is uncursed.\n\n" +
				"_-_ Reclaim trap no longer grants recharging, now stores the trap instead. The trap can then be triggered anywhere the player likes."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_EMERALD, null), "Other Item Buffs",
				"_-_ Ring of elements now grants 20% resistance per level, up from 16%. However, ring of elements also no longer applies to melee attacks from magic-wielding enemies.\n\n" +
				"_-_ Throwing stone base damage increased to 2-5 from 1-5\n" +
				"_-_ Throwing stone durability increased to 5 from 3\n\n" +
				"_-_ Throwing hammer base damage increased to 10-20 from 8-20"));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_SCALE, new ItemSprite.Glowing( 0x663300 )), "Enchant/Glyph Buffs",
				"_-_ Vampiric now has a chance to heal for large amounts, instead of always healing for small amounts.\n\n" +
				"_-_ Entanglement no longer roots, now only applies herbal armor buff. Amount of herbal armor granted reduced to compensate.\n\n" +
				"_-_ Affection charm duration up to 8-12 from 4-12. This means an affection proc now guarantees a free hit.\n\n" +
				"_-_ Potential no longer grants small amounts of partial charge on every hit, now has a chance to grant one full charge instead. Overall amount of charge given increased by ~20%."));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new Tomahawk(),
				"The Tomahawk has been adjusted to make its damage more upfront, but also to reduce its extreme damage scaling with upgrades.\n\n" +
				"_-_ Tomahawk damage scaling increased to 2-4 per level, up from 2-2\n" +
				"_-_ Tomahawk fully_charged damage now starts at 60% of damage, down from 100%"));
		
		changes.addButton( new ChangeButton( new Image(Assets.WARRIOR, 0, 15, 12, 15), "Warrior Nerfs",
				"Warrior shielding regeneration scaling reduced. It is now a flat 1 shield every 30 turns. This is a very slight buff to the earlygame, and a significant nerf to the lategame.\n\n" +
				"I made this change as too much of the warrior's power was put into his base class, and into a passive ability that players tend to ignore. By removing this power, I can put more power into the warrior's subclasses, which should make the warrior feel more fun and interesting without significantly nerfing him overall."));
		
		changes.addButton( new ChangeButton( new Image(Assets.TERRAIN_FEATURES, 16, 0, 16, 16), "Trap Adjustments!",
				"Several traps have been slightly adjusted due to reclaim trap's new functionality:\n\n" +
				"_-_ Disintegration trap no longer deals damage based on target HP\n" +
				"_-_ Flock trap duration no longer scales with depth\n" +
				"_-_ Bosses now resist grim traps, Yog is immune\n" +
				"_-_ Pitfall traps do not work on boss floors\n" +
				"_-_ Reduced poison dart trap damage scaling\n" +
				"_-_ Rockfall traps trigger in a 5x5 AOE when cast from reclaim trap\n" +
				"_-_ Bosses will resist weakening traps"));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_PLATE, new ItemSprite.Glowing( 0x660022 )), "Enchant/Glyph Nerfs",
				"_-_ Chilling now only stacks the chilled debuff up to 6 turns.\n\n" +
				"_-_ Thorns now bleeds enemies for a set amount based on armor level, instead of scaling with damage dealt.\n\n" +
				"_-_ Antimagic no longer affects the melee attacks of magic wielding enemies.\n" +
				"_-_ Antimagic no longer bases its blocking power on armor directly, now uses its own calculation which scales on level. This is a slight boost for lower tier armors and a nerf for higher tier ones."));
	}
	
	public static void add_v0_7_2_Changes( ArrayList<ChangeInfo> changeInfos ){
		ChangeInfo changes = new ChangeInfo("v0.7.2", true, "");
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Developer Commentary",
				"_-_ Released Mar 18th, 2019\n" +
				"_-_ 90 days after Shattered v0.7.1\n" +
				"\n" +
				"Dev commentary will be added here in the future."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.POTION_CATALYST, null), "Catalysts!",
				"Added two new recipes: _Alchemical Catalysts_ and _Arcane Catalysts._\n\n" +
				"These catalysts are made with any potion/scroll, and a seed/runestone. They replace many specific items for higher-cost recipes. Alchemy should be much more flexible now!\n\n" +
				"Additional Alchemy Changes:\n\n" +
				"When a recipe asks for any item of a certain type that item no longer has to be identified.\n\n" +
				"Alchemy guidebook pages now spawn more slowly at earlier stages of the game, and significantly faster at later stages of the game."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.LONGSWORD, new ItemSprite.Glowing(0x0000FF)), "Enchantment Overhaul!",
				"Enchantments have been significantly rebalanced to be less about direct damage and more about utility and situational power. Their design should now be more similar to glyphs.\n\n" +
				"Buffed Enchants: Chilling, Lucky.\n\n" +
				"Nerfed Enchants: Blazing, Shocking, Grim, Vampiric\n\n" +
				"Removed Enchants: Vorpal, Venomous, Dazzling, Eldritch, and Stunning.\n\n" +
				"New Enchants: Blocking, Blooming, Elastic (formerly a curse), Precise, and Swift.\n\n" +
				"New Curse: Polarized.\n\n" +
				"Some battlemage effects have been adjusted to accommodate these new enchantments. Most of these are very minor, except staff of regrowth, which now procs blooming."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.MAGIC_INFUSE, null), "Enchantment Adjustments",
				"_-_ Significantly adjusted when enchants/glyphs are lost when items are upgraded. Items are now always safe up to +4, then have a growing chance until +8 where enchantment loss is guaranteed.\n\n" +
				"_-_ Upgrades now have a set 33% chance to cleanse curses, instead of a chance which scales with level.\n\n" +
				"Magical Infusion spell adjusted:\n" +
				"_-_ Recipe changed to: upgrade + catalyst + 4 energy.\n" +
				"_-_ No longer applies an enchant/glyph, instead is guaranteed to preserve one while upgrading."));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.BREW_INFERNAL, null), "Combination Items",
				"The following combination items are no longer craftable, and are effectively removed from the game:\n" +
				"_-_ Wicked Brew\n" +
				"_-_ Frigid Brew\n" +
				"_-_ FrostFire Brew\n" +
				"_-_ Elixir of Restoration\n" +
				"_-_ Elixir of Vitality\n\n" +
				"These items offered no unique gameplay and existed purely to give a few cheap recipes. Thanks to catalysts filling that role, they no longer have a reason to exist. FrostFire Brew in particular may return in some form."));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"_-_ The Identification system has been adjusted to require EXP gain in addition to item uses. " +
				"This change prevents exploits where an item could be used in unintended ways to rapidly ID it. " +
				"Items should ID at about the same rate if exp is gained while using them.\n\n" +
				"_-_ Increased the max level to gain exp from gnoll brutes and cave spinners by 1.\n\n" +
				"_-_ Sniper's mark now lasts for 2 turns, up from 1. This should make it easier to use with slow weapons, or while slowed.\n\n" +
				"Elixir of Might reworked:\n" +
				"_-_ Recipe changed to: strength + catalyst + 5 energy\n" +
				"_-_ Health boost now scales up with level, but fades after the hero gains a few levels\n\n" +
				"_-_ Meat Pie recipe cost reduced from 9 to 6, total healing reduced from 45 to 25\n\n" +
				"_-_ Added a privacy policy link to the Google Play edition of Shattered."));
		
		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed:\n" +
				"_-_ Various rare crash bugs\n" +
				"_-_ Various minor visual bugs\n" +
				"_-_ Grim enchant activating when an enemy is already dead\n" +
				"_-_ Burning destroying scrolls when the hero is immune to it\n" +
				"_-_ Chasms killing enemies which are already dead in some cases\n" +
				"_-_ Thieves not correctly interacting with quickslotted items\n" +
				"_-_ Screen orientation not always being set when game starts\n" +
				"_-_ Flying characters pushing the ground after teleporting\n" +
				"_-_ Bombs rarely damaging tengu multiple times\n" +
				"_-_ Thrown weapons instantly breaking in rare cases\n" +
				"_-_ Dwarf King summoning skeletons while frozen\n" +
				"_-_ Incorrect behaviour when wands recharge very quickly\n" +
				"_-_ Thieves rarely escaping when they are close\n" +
				"_-_ Beacon of returning losing set location when scroll holder is picked up\n" +
				"_-_ Recycle not giving an item if inventory is full\n" +
				"_-_ Rare cases where the game wouldn't save during alchemy"));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.LANGS), Messages.get(ChangesScene.class, "language"),
				"Updated Translations\n\n" +
				"Updated Translator Credits"));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"Major internal improvements to service integrations for Google Play version of the game:\n" +
				"_-_ 'Share Gameplay Data' now uses Google Firebase Analytics instead of older Google Analytics. Data collected is unchanged.\n" +
				"_-_ Many internal improvements to Google Play Games sync and Google Payment integration.\n" +
				"_-_ Item renaming donation perk now applies to wands.\n\n" +
				"_-_ Added support for adaptive icons in Android 8.0+.\n" +
				"_-_ Improved how the game handles orientation changes and window resizing.\n" +
				"_-_ Shocking enchantment no longer visually arcs lightning to the hero."));
		
		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed (existed before 0.7.2):\n" +
				"_-_ Cloak of Shadows very rarely consuming more charges than it should\n" +
				"_-_ Assassin's blink not working on enemies standing on traps\n" +
				"_-_ Glyph of stone blocking an incorrect amount of damage (too low) in some cases\n" +
				"_-_ Hourglass not updating charges correctly in some cases\n" +
				"_-_ Blandfruit bush rarely appearing in 'on diet' challenge\n" +
				"_-_ Strength from ring of might not appearing in rankings\n" +
				"_-_ Multiplicity curse spawning rats on floor 5\n" +
				"_-_ Dried rose rarely being usable before completing ghost quest\n" +
				"_-_ Corrupted thieves being able to steal from the hero\n" +
				"_-_ Rare crashes involving rankings windows\n" +
				"_-_ Crashes and other odd behaviour when a berserking hero is affected by shielding buffs\n" +
				"_-_ Tengu spawning on top of other characters\n" +
				"_-_ Cloak of shadows only being usable from quickslots if it has 1 charge"));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new WandOfTransfusion(),
				"Wand of Transfusion changed significantly when used on enemies:\n" +
				"_-_ No longer self-harms, now grants a mild self-shield instead\n" +
				"_-_ Charm duration no longer scales with level, damage to undead enemies reduced"));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_AMETHYST, null), Messages.get(RingOfWealth.class, "name"),
				"Ring of Wealth significantly buffed:\n" +
				"_-_ Special item drops now happen ~50% more often\n" +
				"_-_ The ring of wealth now awards a greater variety of items from special drops\n" +
				"_-_ special wealth drops have a 1/10 chance to award a high value item\n" +
				"_-_ Wraiths and minion enemies no longer have a chance to generate wealth items"));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SPEAR, new ItemSprite.Glowing(0x00FFFF)), "Buffed Enchants",
				"_-_ Chilling now stacks with itself over multiple procs\n\n" +
				"_-_ Lucky buffed/reworked. No longer affects damage, now generates bonus items when enemies are killed with a lucky weapon."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SEED_SWIFTTHISTLE, null), "Item Balance Adjustments",
				"Several seeds and stones have been buffed:\n" +
				"_-_ Player can now move without cancelling swiftthistle's effect\n" +
				"_-_ Duration of poison from sorrowmoss increased by ~33%\n" +
				"_-_ Increased the strength of warden's earthroot effect\n" +
				"_-_ Stone of clairvoyance no longer disarms traps, now goes through walls instead\n" +
				"_-_ Stone of detect curse is reworked, now stone of disarming. Disarms up to 9 traps where it is thrown.\n" +
				"_-_ Stone of aggression now forces enemies to attack a target. Duration is longer if thrown at allies.\n\n" +
				"_-_ Scroll of teleportation now teleports the player to the entrance of secret/special rooms instead of into them\n\n" +
				"_-_ Blessed ankhs now cure the same debuffs as a potions of healing\n\n" +
				"Fire and toxic gas have been adjusted to deal damage based on dungeon depth, and not target max health. " +
				"This means more damage versus regular enemies, and less versus bosses. " +
				"Several bosses have lost their resistances to these effects as a result of this change."));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.DIRK, new ItemSprite.Glowing(0xFF4400)), "Nerfed Enchants",
				"_-_ Blazing no longer deals direct damage, now instead is more likely to set enemies on fire.\n\n" +
				"_-_ Shocking no longer deals damage to enemy being attacked, deals more damage to surrounding enemies.\n\n" +
				"_-_ Vampiric now grants less health when hero is at higher HP.\n\n" +
				"_-_ Grim is now more likely to 'finish off' an enemy, but is less likely to activate at higher enemy health."));
	}
	
	public static void add_v0_7_1_Changes( ArrayList<ChangeInfo> changeInfos ){
		ChangeInfo changes = new ChangeInfo("v0.7.1", true, "");
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Developer Commentary",
				"_-_ Released Dec 18th, 2018\n" +
				"_-_ 61 days after Shattered v0.7.0\n" +
				"\n" +
				"Dev commentary will be added here in the future."));
		
		changes.addButton( new ChangeButton( new Image(Assets.HUNTRESS, 0, 15, 12, 15), "Huntress Reworked!",
				"The Huntress has received a class overhaul!\n\n" +
				"Her boomerang has been replaced with a bow. The bow has infinite uses, like the boomerang, but cannot be upgraded directly, instead it will grow stronger as the huntress levels up.\n\n" +
				"Her knuckledusters have been replaced with studded gloves. This change is purely cosmetic.\n\n" +
				"Those with runs in progress will have their boomerang turn into a bow, and will regain most of the scrolls of upgrade spent on the boomerang.\n\n" +
				"The huntress can now also move through grass without trampling it (she 'furrows' it instead)."));
		
		changes.addButton( new ChangeButton( new Image(Assets.HUNTRESS, 0, 90, 12, 15), "Huntress Subclasses Reworked!",
				"Huntress subclasses have also received overhauls:\n\n" +
				"The Sniper can now see 50% further, penetrates armor with ranged attacks, and can perform a special attack with her bow.\n\n" +
				"The Warden can now see through grass and gains a variety of bonuses to plant interaction."));
		
		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.TRIDENT, null), "Thrown Weapon Improvements",
				"Thrown weapons now show their tier, ranging from 1-5 like with melee weapons.\n\n" +
				"All Heroes now benefit from excess strength on thrown weapons.\n\n" +
				"Thrown weapons now get +50% accuracy when used at range.\n\n" +
				"Thrown weapons can now be upgraded!\n" +
				"_-_ Upgrades work on a single thrown weapon\n" +
				"_-_ Increases damage based on tier\n" +
				"_-_ Gives 3x durability each upgrade\n" +
				"_-_ Weapons with 100+ uses now last forever\n" +
				"_-_ Darts are not upgradeable, but tipped darts can get extra durability\n\n" +
				"Ring of sharpshooting has been slightly adjusted to tie into this new upgrade system."));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(BadgeBanner.image(Badges.Badge.UNLOCK_MAGE.image), "Hero Class changes",
				"All heroes except the warrior now need to be unlocked via new badges. The requirements are quite simple, with the goal of giving new players some early goals. Players who have already unlocked characters will not need to re-unlock them.\n\n" +
				"To help accelerate item identification for alchemy, all heroes now start with 3 identified items: The scroll of identify, a potion, and another scroll."));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"Added a partial turn indicator to the game interface, which occupies the same spot as the busy icon. This should make it much easier to plan actions that take more or less than 1 turn.\n\n" +
				"Rings now have better descriptions for their stats! All rings now show exactly how they affect you in a similar way to how other equipment gives direct stats.\n\n" +
				"Precise descriptions have been added for weapons which block damage.\n\n" +
				"Added item stats to the item catalog.\n\n" +
				"Dropping an item now takes 1 turn, up from 0.5 turns."));
		
		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed:\n" +
				"_-_ various crash bugs\n" +
				"_-_ various minor visual bugs\n" +
				"_-_ recycle being able to produce health potions with pharmacophobia enabled\n" +
				"_-_ magical porter soft-locking the game in rare cases\n" +
				"_-_ mystical energy recharging some artifacts incorrectly\n" +
				"_-_ health potion limits not applying to prison guards\n" +
				"_-_ traps with ground-based effects affecting flying characters\n" +
				"_-_ odd behaviour when transmuting certain items\n" +
				"_-_ keys rarely spawning without chests\n" +
				"_-_ fireblast rarely damaging things it shouldn't\n" +
				"_-_ dew drops from dew catchers landing on stairs\n" +
				"_-_ ankh revive window rarely closing when it shouldn't\n" +
				"_-_ flock and summoning traps creating harsh sound effects\n" +
				"_-_ thrown weapons being lost when used on sheep\n" +
				"_-_ various specific errors when actions took more than 1 turn\n" +
				"_-_ various freeze bugs caused by Tengu"));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.LANGS), Messages.get(ChangesScene.class, "language"),
				"Updated translations\n\n" +
				"Updated translator credits"));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton( new Image(Assets.ROGUE, 0, 15, 12, 15), "Hero Buffs",
				"_-_ Rogue's cloak of shadows base charge speed increased by ~11%, scaling reduced to compensate.\n\n" +
				"_-_ Warlock's soul mark base chance increased to 15% from 10%, scaling reduced to compensate.\n\n" +
				"_-_ Warlock's soul mark hunger restoration increased by 100%, health restoration increased by 33%."));
		
		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_TOPAZ, null), "Various Item Buffs",
				"_-_ Ring of energy simplified/buffed. Now grants a flat +25% charge speed per level, instead of +1 effective missing charge per level\n\n" +
				"_-_ Ring of elements power increased to 16% from 12.5%\n\n" +
				"_-_ Ring of wealth 'luck' bonus increased to 20% from 15%\n\n" +
				"_-_ Bolas base damage increased to 6-9 from 4-6\n\n" +
				"_-_ Wand of regrowth now spawns furrowed grass when it begins to run out of energy due to excessive use, instead of short grass.\n\n" +
				"Wand of fireblast buffed:\n" +
				"_-_ shot distance at 3 charges reduced by 1\n" +
				"_-_ damage at 1 charge reduced slightly\n" +
				"_-_ damage at 2/3 charges increased by ~15%"));
		
		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.ARMOR_LEATHER, new ItemSprite.Glowing(0x222222)), "Other Buffs",
				"_-_ vorpal enchant fully_charged reduced by 20%\n\n" +
				"_-_ glyph of potential wand charge bonus increased by 20%\n\n" +
				"_-_ glyph of stone evasion conversion efficiency increased to 75% from 60%"));
		
		changes.addButton( new ChangeButton(new Image(Assets.KING, 1, 0, 14, 16), "Dwarf King",
				"While I would like to make more extensive changes to Dwarf King in the future, I've made a couple smaller tweaks for now to make him harder to cheese:\n\n" +
				"_-_ Dwarf King is now able to summon skeletons even if he cannot see the hero\n" +
				"_-_ Dwarf King is now resistant to fire and toxic gas"));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton( new Image(Assets.WARRIOR, 0, 15, 12, 15), "Warrior Nerfs",
				"_-_ Warrior's shielding regen scaling reduced by ~15%. This is primarily a lategame nerf."));
		
		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_RUBY, null), "Ring Nerfs",
				"Ring of furor has been nerfed/simplified:\n" +
				"_-_ Now provides a flat +10.5% attack speed per level, instead of speed which scales based on how slow the weapon is.\n\n" +
				"This means the ring is effectively nerfed for slow weapons and regular weapons, and slightly buffed for fast weapons. A +6 ring grants almost exactly doubled attack speed.\n\n\n" +
				"The ring of force's equipped weapon bonus was always meant as a small boost so it wasn't useless if the player already had a better weapon. It wasn't intended to be used to both replace melee and then boost thrown weapons.\n" +
				"_-_ The ring of force no longer gives bonus damage to thrown weapons."));
		
		changes.addButton( new ChangeButton( new Gauntlet(),
				"As furor now works much better with fast weapons, I've taken the opportunity to very slightly nerf sai and gauntlets\n\n" +
				"_-_ Sai blocking down to 0-2 from 0-3\n" +
				"_-_ Gauntlet blocking down to 0-4 from 0-5"));
		
		changes.addButton( new ChangeButton( new Shuriken(),
				"Shuriken have been adjusted due to the new upgrade system:\n\n" +
				"_-_ Base damage increased to 4-8 from 4-6\n" +
				"_-_ Durability reduced to 5 from 10"));
	}
	
	public static void add_v0_7_0_Changes( ArrayList<ChangeInfo> changeInfos ){
		ChangeInfo changes = new ChangeInfo("v0.7.0", true, "");
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Developer Commentary",
				"_-_ Released Oct 18th, 2018\n" +
				"_-_ 501 days after Shattered v0.6.0\n" +
				"_-_ 168 days after Shattered v0.6.5\n" +
				"\n" +
				"Dev commentary will be added here in the future."));
		
		changes.addButton( new ChangeButton(new Image(Assets.TILES_SEWERS, 48, 96, 16, 16 ), "Alchemy Overhaul!",
				"The game's alchemy system has been entirely overhauled!\n\n" +
				"Alchemy is now a full consumable crafting system which lets you create all kinds of new items.\n\n" +
				"There is also a new resource: alchemical energy. Every alchemy pot has some energy within it. Some recipes require this energy, so make sure to use it wisely!\n\n" +
				"All of this is explained in a new guidebook specifically for alchemy. Pages of it can be found in alchemy rooms. Existing players will be given some pages automatically to get started."));
		
		changes.addButton( new ChangeButton(new AlchemistsToolkit(),
				"The Alchemist's Toolkit returns!\n\n" +
				"The toolkit can be found like any other artifact, and acts as a sort of horn of plenty for the new alchemical energy resource."));
		
		changes.addButton( new ChangeButton(new Image(Assets.TERRAIN_FEATURES, 32, 112, 16, 16), "New Consumables",
				"Added a new scroll, potion, and plant!\n\n" +
				"_-_ Scroll of transmutation is a rare scroll which allows the user to change an item into another one of the same type. Note that it cannot be used to make scrolls of magical infusion.\n\n" +
				"_-_ Potion of haste is an uncommon potion which grants a temporary burst of speed.\n\n" +
				"_-_ Swiftthistle is the plant counterpart to potions of haste. Both the plant and tipped dart give various speed or time-based buffs."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.STONE_BLINK, null), "Runestones",
				"Added 10 new runestones, and runestone crafting!\n\n" +
				"Two or three runestones can be crafted by using a scroll with an alchemy pot.\n\n" +
				"Runestones give various effects that are similar in theme to their scroll counterpart.\n\n" +
				"Runestones also naturally appear in alchemy rooms, and a new special room type."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.EXOTIC_AMBER, null), "Exotic Potions",
				"Added 12 new potions which can be created through alchemy!\n\n" +
				"Mix a potion and any two seeds to create an exotic potion with unique effects.\n\n" +
				"Exotic Potions are only available through alchemy, or by transmuting a regular potion."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.EXOTIC_ISAZ, null), "Exotic Scrolls",
				"Added 12 new scrolls which can be created through alchemy!\n\n" +
				"Mix a scroll and any two runestones to create an exotic scroll with unique effects.\n\n" +
				"Exotic Scrolls are only available through alchemy, or by transmuting a regular scroll."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RETURN_BEACON, null), "New Recipes!",
				"Added ~40 other items which can be created through alchemy!\n\n" +
				"Most of these recipes require alchemical energy, and information about them can be found within alchemy guidebook pages in the prison and deeper in the dungeon.\n\n" +
				"All of these items are only available through alchemy."));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_ARMBAND, null), "Spawn Rate Changes",
				"_-_ Master Thieves' Armband is now a regularly dropping artifact.\n" +
				"_-_ Thieves now rarely drop a random ring or artifact instead of the armband.\n\n" +
				"_-_ Blandfruit seeds and wells of transmutation have been removed.\n" +
				"_-_ Potion of Might and Scroll of Magical infusion are now produced through alchemy.\n" +
				"_-_ Transmuting potions/scrolls now gives their exotic variant, and vice-versa.\n\n" +
				"_-_ One runestone of enchantment and one runestone of intution are guaranteed per run.\n" +
				"_-_ Potion and scroll drops are now slightly more varied.\n" +
				"_-_ Reduced the droprate of bombs.\n\n" +
				"_-_ Adjusted enchant/glyph probabilities slightly. rare ones should be slightly more common.\n\n" +
				"_-_ There is now a guaranteed alchemy room every chapter."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_BEACON, null), "Boss reward changes",
				"Boss rewards have been significantly adjusted:\n\n" +
				"_-_ Lloyd's beacon and Cape of Thorns no longer drop, they are effectively removed from the game.\n\n" +
				"_-_ Goo and DM-300 now drop unique alchemy ingredients instead.\n\n" +
				"_-_ Lloyd's beacon has been replaced by alchemy recipes, Cape of Thorns will likely return in some form in the future."));
		
		changes.addButton( new ChangeButton(new Blandfruit(),
				"Blandfruit has been changed to be more consistent with potions.\n\n" +
				"All blandfruit types now exactly mimic their potion counterparts, there are now no blandfruit-exclusive effects.\n\n" +
				"When a thrown blandfruit shatters, it will now leave behind blandfruit chunks, which can be eaten. This allows offensive blandfruits to be used without losing their food value.\n\n" +
				"The previous unique mechanics of earthfruit, sorrowfruit, and firefruit have been recycled into the new alchemy system."));
		
		changes.addButton( new ChangeButton(new UnstableSpellbook(),
				"The unstable spellbook has received a mini-rework to go along with the new exotic scrolls.\n\n" +
				"_-_ Previous enhanced scroll mechanic removed.\n\n" +
				"_-_ Feeding a scroll to the spellbook now allows you to use either that scroll, or its exotic equivalent.\n\n" +
				"_-_ Using the exotic variant of a scroll costs 2 charges instead of 1.\n\n" +
				"_-_ Charge speed at low levels increased. Max charges increased to 8 from 6."));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"_-_ Potions which should be thrown can now be thrown from the quickslot, if they are IDed.\n" +
				"_-_ Thrown items and wand zaps now go through tall grass.\n" +
				"_-_ Expanded what items bags can carry. Most alchemy produce can fit in a bag, magical holster now holds bombs.\n\n" +
				"_-_ Caustic ooze now lasts a max of 20 turns.\n" +
				"_-_ Bleeding damage is now more consistent.\n\n" +
				"_-_ Adjusted the text for breaking paralysis.\n"+
				"_-_ Adjusted various potion/plant/seed sprites.\n" +
				"_-_ Healing now has an icon and description.\n" +
				"_-_ Improved the layering system for raised terrain like grass.\n" +
				"_-_ Added an ingame version indicator.\n" +
				"_-_ Added a new indicator for when an item is not identified, but known to be uncursed.\n\n" +
				"_-_ Improved payment & sync functions on Google Play version.\n\n" +
				"_-_ Adjusted bone pile functionality to make it more clear that a spawning wraith means an item is cursed."));
		
		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed:\n" +
				"_-_ Various rare crash and freeze bugs\n" +
				"_-_ Various audio and visual bugs\n" +
				"_-_ Sad Ghost attacking nonexistent enemies\n" +
				"_-_ Various rare cases where item windows could stack\n" +
				"_-_ Cases where projectiles would disappear\n" +
				"_-_ Multiplicity curse duplicating projectiles\n" +
				"_-_ Lucky enchant not correctly scaling with upgrades\n" +
				"_-_ Various effects incorrectly working on dead characters\n" +
				"_-_ Wands never appearing in heroes remains\n" +
				"_-_ Remains rarely appearing inside bookcases on floor 20\n" +
				"_-_ Wand of corruption doing nothing to corrupted enemies\n" +
				"_-_ Augmented weapons rarely having inconsistent speed\n" +
				"_-_ Scroll of upgrade revealing curses on unidentified items\n" +
				"_-_ Item curses rarely not being revealed when they should be\n" +
				"_-_ Assassin buffs not being cleared when they should in some cases\n" +
				"_-_ Rooting not working correctly on retreating enemies\n" +
				"_-_ Searching spending hunger in a locked level\n" +
				"_-_ 'Faith is my armor' deleting class armors\n" +
				"_-_ Various cases where the player can be ontop of enemies"));
		
		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"Fixed:\n" +
				"_-_ Various screen layout issues in power saver mode\n" +
				"_-_ Crashes when tengu is healed above 1/2 health\n" +
				"_-_ Bolas incorrectly requiring 15 strength\n" +
				"_-_ Non-heroes being able to use reach weapons through walls\n" +
				"_-_ Antimagic glyph applying to more effects when used by the sad ghost\n" +
				"_-_ Some items not being known as uncursed when sold from shops\n" +
				"_-_ Obfuscation glyph not improving every upgrade\n" +
				"_-_ Magical sleep rarely cancelling paralysis\n" +
				"_-_ Exploits where bone piles could be used to check if an item was cursed"));
		
		changes.addButton( new ChangeButton(Icons.get(Icons.LANGS), Messages.get(ChangesScene.class, "language"),
				"Updated Translations\n\nUpdated translator credits\n\nAdded new language: Basque!"));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new WandOfTransfusion(),
				"Wand of transfusion has been rebalanced, with an emphasis on making it much more useful in conjunction with weaker allies:\n\n" +
				"_-_ Using the wand still costs 10% max hp\n\n" +
				"_-_ Ally healing adjusted to 10% of user max HP + a flat 3 per level, from 30% + 3%/lvl missing hp\n\n" +
				"_-_ Ally healing can now overheal up to whatever the max healing per shot is\n\n" +
				"_-_ Undead damage is is now the same as ally healing, from 30% + 5%/lvl max hp\n\n" +
				"_-_ Charming is now more powerful at higher wand levels\n\n" +
				"_-_ All other transfusion functionality has been removed"));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SCROLL_KAUNAN, null), new ScrollOfTeleportation().trueName(),
				"The scroll of teleportation has been buffed. It now prioritizes sending the user to rooms they have not seen yet, and can teleport to secret rooms."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SCROLL_ODAL, null), new ScrollOfMirrorImage().trueName(),
				"Scroll of mirror image has been adjusted to have more interactions with other items, but to also be less powerful at base:\n\n" +
				"_-_ Scroll now spawns 2 images, down from 3\n\n" +
				"_-_ Mirror images now attack with the hero's weapon, at 50% damage\n\n" +
				"_-_ Images no longer fade after a successful attack, instead they pull enemy aggro\n\n" +
				"_-_ Images start out invisible, have 1 hp, no blocking power, but do inherit some of the hero's evasion."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SCROLL_NAUDIZ, null), new ScrollOfTerror().trueName(),
				"Terror now has it's duration reduced by 5 whenever damage is taken, rather than being removed entirely. Scroll of terror duration has been increased to 20 from 10.\n\n" +
				"Charm now has it's duration reduced by 5 whenever damage is taken, rather than not losing any duration. Succubi have been given a life-drain ability in compensation, and various charming effects have had their durations adjusted."));
		
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(new WandOfRegrowth(),
				"Wand of regrowth will now cease producing plants if it is overused. Charges spent before it begins degrading will increase if the wand is upgraded. At +12 the wand will function infinitely.\n\n" +
				"This change is made to combat farming with low-levelled wands of regrowth. Especially with the alchemy changes this would be far too powerful. Infinite farming is still possible, but requires upgrades."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SCROLL_GYFU, null), new ScrollOfRetribution().trueName(),
				"The scroll of psionic blast is now known as the scroll of retribution:\n" +
				"_-_ removed damage and stun penalty, now self-weakens instead\n" +
				"_-_ now blinds enemies as well as the player\n" +
				"_-_ damage dealt now scales with missing player HP. At very low HP scroll is still an instakill on most enemies\n\n" +
				"Scroll of psionic blast still exists however. It is now an exotic scroll!"));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.POTION_CRIMSON, null), new PotionOfHealing().trueName(),
				"_-_ Speed of healing effects (e.g. potion of healing) have been reduced slightly. Overall heal amounts unchanged."));
		
		changes.addButton( new ChangeButton(new Honeypot(),
				"Bees were never intended to be used as a boss-killing tool by stacking many of them onto one area. This use has now been restricted:\n" +
				"_-_ Bees are now hostile to eachother\n\n" +
				"Note that the new alchemy system may have a recipe which helps calm angry bees down..."));
	}
}
